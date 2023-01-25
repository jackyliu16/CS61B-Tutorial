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
import java.util.HashMap;
import java.util.Objects;

import static gitlet.Helper.BLOB_FOLDER;
import static gitlet.Helper.CWD;
import static gitlet.Helper.REPO;

public class StatusController implements Serializable {
    static final String SAVE_FILE = "StatusCache";
    static final Logger log = Logger.INSTANCE;

    Branch current;
    HashMap<String, String> stagedFile = new HashMap<>();
    HashMap<String, String> removedFile = new HashMap<>();
//    HashMap<String, Long> lastModified = new HashMap<>();   // for all file under the work directory

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
            String hash = Utils.sha1(Utils.serialize(inp));
            log.info("hash %s", hash);
            // if the file path in mapping, compare the hash from mapping and collect this time.
            if (stagedFile.containsKey(fileName) && Objects.equals(stagedFile.get(fileName), hash)) {
                log.info("stagedFile contains file and the map is same as %s ", hash);
                /// same -> do nothing
                log.info("nothing to update");
                return;
            } else {
                /// else -> copy the file in blob and remove the original file
                stagedFile.remove(fileName);
            }
            System.out.println(Objects.equals(stagedFile.get(fileName), hash));
            /// else if the path wasn't in mapping -> copy the file in to the file with hash as it's name.

            // copy file into blob
            File des = Utils.join(REPO, BLOB_FOLDER, hash);
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
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Add a file into removeFile
     * 1. TODO To be improved
     * @param fileName
     */
    public void deleteFile(String fileName) {

    }

    /**
     * Commit the change in the stage area such as staged File and removed File
     * 1. add the file from the staged File into Blob (BC when we add a file we need to calculate if the file hash has been change, thus we need to makeup a kind of class to save the file information ?)
     * 2. remove the removed File from removedFile
     * 3. check if the file in the lastest commit has been change
     */
    public void commit() {

    }

    // NOTE: we may need to make up another class to save the information of each file current in the list,
    //  that we could check if the file has been change.
    //  but this action could also be replace by we change the hash each time and make it as the file name
    //  so in this ways i want to using a kind of map like HashMap<String, String>(file path) .

    public String toString() {
        return String.format(
                """ 
                StatusController:
                Branch: %s \tstageFile: %s
                \tremovedFile: %s
                """,
                current,
                stagedFile,
                removedFile
        );
    }
}