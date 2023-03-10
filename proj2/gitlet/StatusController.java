package gitlet;
/*
 * @File:   Status.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/20 上午12:08
 * @Version:0.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static gitlet.Helper.*;

public class StatusController implements Serializable {
    static final Logger log = Logger.INSTANCE;

    Branch current;
    HashMap<String, String> stagedFile = new HashMap<>();
    HashMap<String, String> removedFile = new HashMap<>();

    public StatusController() {
        current = Helper.getCurrent();
    }

    /**
     * Add a file into stagedFile map
     * 1. if exist then overwrite
     * 2. detect and save the last change time of the file, if the file has change then check if it's same as the last comment.
     * @param fileName the path string of relative address from the CWD
     */
    public void addFile(String fileName) {
        // if the input file exist.
        File inp = Utils.join(CWD, fileName);
        log.trace("%s", inp);
        // if input file exist
        if (inp.exists()) {
            // collect the hash of the file content.
            String hash = null;
            try {
                hash = Utils.sha1(Files.readAllBytes(inp.toPath()));
            } catch (IOException e) {
                log.error("IOException");
            }
            log.debug("hash %s", hash);

            // if the fileName is in the removedFile list
            log.debug("fileName: %s", fileName);
            if (removedFile.containsKey(fileName)) {
                removedFile.remove(fileName);
            }

            // nothing change since the latest commit
            if (getCurrent().commit.checkIfSame(fileName, hash)) {
                log.info("the add file is as same as the file in the latest commit");
                return;
            }

            // if the file path in mapping, compare the hash from mapping and collect this time.
            if (stagedFile.containsKey(fileName) && Objects.equals(stagedFile.get(fileName), hash)) {
                /// same -> do nothing
                log.info("stagedFile contains file and the map is same as %s ", hash);
                return;
            } else {
                /// else -> copy the file in blob and remove the original file
                stagedFile.remove(fileName);
            }

            /// else if the path wasn't in mapping -> copy the file in to the file with hash as it's name.

            // copy file into cache folder
            File des = Utils.join(REPO, CACHE_FOLDER, hash);
            assert !des.exists();
            try {
                Helper.copyFile(inp, des);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error("FileNotFoundException");
            } catch (IOException e) {
                e.printStackTrace();
                log.error("IOException");
            }
            assert des.exists();
            stagedFile.put(fileName, hash);
            log.debug("%s", stagedFile);
        } else {
            // TODO return failure if the file is not exist
            exitProgramWithMessage("File does not exist.");
        }
    }

    /**
     * just add a file into removeFile
     * @param fileName the file name you want do delete
     */
    public void deleteFile(String fileName) {
        boolean flag = false;
        // if stage for addition then remove
        if (stagedFile.containsKey(fileName)) {
            log.debug("file in stagedFile");
            flag = true;
            stagedFile.remove(fileName);
            try {
                File file = Utils.join(REPO, CACHE_FOLDER, Utils.sha1((Object) Files.readAllBytes(Utils.join(CWD, fileName).toPath())));
                assert file.exists();
                file.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // if in the track of current commit then stage it for remove and remove from work directory
        Commit latestCommit = getCurrent().getLatestCommit();
        if (latestCommit.containsFile(fileName)) {
            log.debug("file in the latest Commit");
            assert !flag;
            flag = true;
            // TODO if we should not using the hashmap for example we just using arrayList
            removedFile.put(fileName, latestCommit.getMapping().get(fileName));
            File file = Utils.join(CWD, fileName);
            file.delete();
        }

        // if the file haven't been addition and haven't track in the haed commit. -> No reason to remove the file.
        if (!flag) exitProgramWithMessage("No reason to remove the file.");
    }

    /**
     * Commit the change in the stage area such as staged File and removed File
     * 1. add the file from the staged File into Blob (BC when we add a file we need to calculate if the file hash has been change, thus we need to makeup a kind of class to save the file information ?)
     * 2. remove the removed File from removedFile
     * 3. check if the file in the lastest commit has been change
     */
    public void commit(String message) {
        log.debug("commit args[1]: %s", message);
        // 1. copy all the file inside the cache and rename it into blob folder
        // 2. clear the mapping
        // 3. remove the file which has the same hash as removedFile mapping.

        // copy the lastest commit
        Branch current = Helper.getCurrent();
        Commit newCom = Commit.appendCommit(current.commit, false, message);  // BC the lastest commit saving the mapping between blob and file path, thus you copy the commit struct should also copy the mapping.
        // NOTE: check if dest file exist: the situation that the file change back to the original part(hash same) -> if same just delete

        // if stagedFile and removedFile is empty (nothing to commit) or some file has been change (reject)
        log.debug("%s", stagedFile);
        log.debug("%s", removedFile);

        // if a file which is in the modify file list and not in the stagedFile and removedFile that we should  exit
        List<String> modifyFiles = newCom.ifFileHasChange();
        List<String> deleteFiles = newCom.ifFileHasDeleted();

        // 1. nothing to commit
        if (stagedFile.isEmpty() && removedFile.isEmpty() && modifyFiles.isEmpty() && deleteFiles.isEmpty()) {
            exitProgramWithMessage("No changes added to the commit.");
        }

        // 2. all file modifyFiles or deleteFiles should case error
        // (all File change not in stagedFile and removedFile should case error)
        for (String fileName: modifyFiles) {
            if (!stagedFile.containsKey(fileName) && !removedFile.containsKey(fileName)) {
                exitProgramWithMessage("Modifications Not Staged For Commit.");
            }
        }
        for (String fileName: deleteFiles) {
            if (!stagedFile.containsKey(fileName) && !removedFile.containsKey(fileName)) {
                exitProgramWithMessage("Modifications Not Staged For Commit.");
            }
        }
//        if (stagedFile.isEmpty() && removedFile.isEmpty() && modifyFiles.isEmpty()) {
//            exitProgramWithMessage("No changes added to the commit.");
//        }
//
//        if (!modifyFiles.isEmpty()) {
//            for (String fileName : modifyFiles) {
//                if (!stagedFile.containsKey(fileName) && !removedFile.containsKey(fileName)) {
//                    Helper.exitProgramWithMessage("Modifications Not Staged For Commit.");
//                }
//            }
//        }


        boolean flag = true; // if false than do nothing
        for (String fileName: stagedFile.keySet()) {
            flag = false;
            File in = Utils.join(REPO, CACHE_FOLDER, stagedFile.get(fileName));
            File out = Utils.join(REPO, BLOB_FOLDER, stagedFile.get(fileName));
            // hash same as it's add time
            try {
                if (in.exists() && !out.exists() && Objects.equals(stagedFile.get(fileName), Utils.sha1((Object) Files.readAllBytes(Utils.join(CWD, fileName).toPath())))) {
                    log.debug("failure when rename cache file into blobs file :\n\t%s\n\t%s", in, out);
                }
            } catch (IOException e) {
                log.error("IOException");
            }
        }

        // add all file from stage file into commit
        for (String fileName: stagedFile.keySet()) {
            flag = false;
            File in = Utils.join(REPO, CACHE_FOLDER, stagedFile.get(fileName));
            File out = Utils.join(REPO, BLOB_FOLDER, stagedFile.get(fileName));
            in.renameTo(out); // TODO should remove assert
            log.info("\nrename\n\t%s into \n\t%s", in, out);
            log.debug(stagedFile.get(fileName));
            log.debug("\n%s", newCom);
//            newCom.mapping.put(fileName, stagedFile.get(fileName)); // add the map between blob and file path
            newCom.addKeyValueMapping(fileName, stagedFile.get(fileName));
        }

        // remove removedFile from commit
        for (String fileName: removedFile.keySet()) {
            flag = false;
            log.error("remove file ?");
            newCom.removeKeyValueMapping(fileName, removedFile.get(fileName));
        }

        // clear cache
        this.removedFile = new HashMap<>();
        this.stagedFile = new HashMap<>();

        // save commit
        String hash = Utils.sha1(Utils.serialize(newCom));
        Helper.saveContentInFile(Utils.join(REPO, COMMIT_FOLDER, hash), newCom);
        current.commit = newCom;
        Helper.saveContentInFile(Utils.join(REPO, CACHE_FOLDER, HEAD_FILE), current);   // BC the current branch will only in the cache/HEAD

        Helper.addContentIntoLog(
                newCom.toString()
        );
    }

    /**
     * reset a file in a specify file on specify branch
     * @param branchFile the file which store the branch info, such as HEAD or others.
     * @param fileName the file path you want to reset
     */
    public void checkOutFileInBranch(File branchFile, String fileName) {
        assert branchFile.exists();
        File destination = Utils.join(CWD, fileName);

        // BC there is a problem when we reset to a older file, thus we not exit.
        if (destination.exists()) {
            log.debug("the file(%s), which you want to checkout wasn't exist", fileName);
            try {
                destination.createNewFile();
            } catch (IOException e) {
                log.error("IOException when create %s", destination);
            }
        }

        // get the branch from store file
        Branch branch = Utils.readObject(branchFile, Branch.class);
        log.debug("we are checkout file in %s branch", branch.name);

        // get file hash from the latest commit of specify branch
        String hash = branch.getFileHash(fileName);

        // copy the file from blob and overwrite the file in
        File origin = Utils.join(REPO, BLOB_FOLDER, hash);
        try {
            Helper.copyFile(origin, destination);
        } catch (IOException e) {
            log.debug("IOException when copy %s to %s", origin, destination);
        }
    }

    public void checkOutAllFileInCommit(Commit commit) {
        // check if there is same file has been change -> reject and exist
        List<String> fileNames = commit.ifFileHasChange();
        Branch current = Helper.getCurrent();
        for (String fileName: fileNames) {
            log.debug("loop");
            if (!current.getLatestCommit().checkIfKeyExistInMapping(fileName)) {
                exitProgramWithMessage("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }

        // 1. delete the file which in current branch but not in that branch
        Commit latestCommitInCurrent = current.getLatestCommit();
        HashMap<String, String> branchMap = commit.getMapping();
        HashMap<String, String> currentMap = latestCommitInCurrent.getMapping();

        for (String fileName: currentMap.keySet()) {
            // [remove] if file only exist on current but not in that branch
            if (!branchMap.containsKey(fileName)) {
                File file = Utils.join(CWD, fileName);
                file.delete();
            }
        }

        // a. if the file exist on current and branch
        for (String fileName: branchMap.keySet()) {
            if (currentMap.containsKey(fileName)) {
                if (fileNames.contains(fileName)) {
                    // [overwrite] if the file has been modified -> overwrite the file from branch into work directory
                    File original = Utils.join(REPO, BLOB_FOLDER, commit.getFileHashIfExist(fileName));
                    File destination = Utils.join(CWD, fileName);
                    try {
                        copyFile(original, destination);
                    } catch (IOException e) {
                        log.error("IOException");
                    }
                } else {
                    // [nothing] if the file haven't changed
                    continue;
                }
            } else {
                // [copy] if the file only contains in branch but not contains in the latest commit of current branch
                File origin = Utils.join(REPO, BLOB_FOLDER, commit.getFileHashIfExist(fileName));
                File destination = Utils.join(CWD, fileName);
                try {
                    copyFile(origin, destination);
                } catch (IOException e) {
                    log.error("IOException");
                }
            }
        }

        // copy the file which only the latest commit
//        for (String fileName: fileNames) {
//            File origin = Utils.join(REPO, BLOB_FOLDER, current.getLatestCommit().getFileHashIfExist(fileName));
//            File destination = Utils.join(CWD, fileName);
//            try {
//                copyFile(origin, destination);
//            } catch (IOException e) {
//                log.error("IOException");
//            }
//        }
    }
    // NOTE: we may need to make up another class to save the information of each file current in the list,
    //  that we could check if the file has been change.
    //  but this action could also be replace by we change the hash each time and make it as the file name
    //  so in this ways i want to using a kind of map like HashMap<String, String>(file path) .

    public String toString() {
        File branchDir = Utils.join(REPO, BRANCH_FOLDER);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Branches ===\n");
        sb.append("*").append(Helper.getCurrent().name).append("\n");

        List<String> branchList = Utils.plainFilenamesIn(branchDir);
        if (!branchList.isEmpty()) {
            for (String name: branchList) {
                sb.append(name).append("\n");
            }
        }

        sb.append("\n").append("=== Staged Files ===\n");
        if (!stagedFile.isEmpty()) {
            for (String name: stagedFile.keySet()) {
                sb.append(name).append("\n");
            }
        }

        sb.append("\n").append("=== Removed Files ===\n");
        if (!removedFile.isEmpty()) {
            for (String name: removedFile.keySet()) {
                sb.append(name).append("\n");
            }
        }

        Branch current = getCurrent();
        Commit commit = current.getLatestCommit();

        List<String> deletedFiles = commit.ifFileHasDeleted();
        List<String> modifyFiles = commit.ifFileHasChange();
        sb.append("\n").append("=== Modifications Not Staged For Commit ===").append("\n");

        // 1. check delete file
        for (String fileName: deletedFiles) {
            if (removedFile.containsKey(fileName)) continue;
            sb.append(fileName).append(" (deleted)").append("\n");
        }

        // 2. check modify file
        for (String fileName: modifyFiles) {
            sb.append(fileName).append(" (modified)").append("\n");
        }

        sb.append("\n").append("=== Untracked Files ===").append("\n");
        List<String> repoFiles = Utils.plainFilenamesIn(CWD);
        if (repoFiles != null) {
            for (String str: repoFiles) {
                if (!(stagedFile.containsKey(str) || removedFile.containsKey(str) || getCurrent().commit.containsFile(str))) {
                    // if the file wasn't in the repo
                    sb.append(str).append("\n");
                }
            }
        }
        return sb.toString();
    }
}