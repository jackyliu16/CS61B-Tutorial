package gitlet;
/*
 * @File:   Commit.java
 * @Desc:   the node of the LinkedList start from the branch pointer
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/18 下午7:33
 * @Version:0.0
 */

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;


import static gitlet.Helper.*;

public class Commit implements Serializable {
    static transient File commitFolder = Utils.join(REPO, Helper.COMMIT_FOLDER);
//    static transient File log = Utils.join(Helper.REPO, Helper.LOG_FILE);
    String logMessage;
//    Timestamp timestamp;
    Date date;
    private HashMap<String, String> mapping;
    Commit prev;

    public Commit(boolean logFlag) {
        // will only been call once (by the initial commit)
        logMessage = "initial commit";
//        timestamp = new Timestamp(-28800000);
        date = new Date(-28800000);
        mapping = new HashMap<>();
        prev = null;
        if (logFlag) log();
    }

    public Commit(String message, boolean logFlag) {
        logMessage = message;
//        timestamp = new Timestamp(System.currentTimeMillis());
        date = new Date(System.currentTimeMillis());
        // TODO finish commit mapping (using copy and add or delete from zone)
        mapping = new HashMap<>();
        prev = null;
        if (logFlag) log();
    }

    public static Commit appendCommit(Commit commit, boolean logFlag, String message) {
        Commit newCommit = new Commit(message, false);
        newCommit.prev = commit;
        newCommit.mapping = new HashMap<>(commit.mapping);
        if (logFlag) newCommit.log();
        return newCommit;
    }

    private void log() {
        // add info into log file
        Helper.addContentIntoLog(toString());

        // save commit node in the file
        saveCommitInFile();
    }

    private void saveCommitInFile() {
        String hash = Utils.sha1((Object) Utils.serialize(this));
        log.debug("saveCommitInFile hash: %s", hash);
        File file = Utils.join(commitFolder, hash);
        Utils.writeObject(file, this);
    }

    /**
     * check if the file has change since the latest commit
     * will not check the file which is not exist(been delete)
     * @return the file name list which
     */
    public List<String> ifFileHasChange() {
        ArrayList<String> res = new ArrayList<>();
        for (String name: mapping.keySet()) {
            String fileHash = mapping.get(name);
            File file = Utils.join(CWD, name);
//            File file = Utils.join(REPO, BLOB_FOLDER, fileHash);
            if (!file.exists()) {
                // if the file not exist that just push into array
                continue;
            }
            try {
                if (!Objects.equals(fileHash, Utils.sha1((Object) Files.readAllBytes(file.toPath())))) {
                    res.add(name);
                }
            } catch (IOException e) {
                log.error("IOException");
                e.printStackTrace();
            }
        }
        log.debug("ifFileHasChange: %s", res.toString());
        return res;
    }

    /**
     * only checking if the file in the commit has been deleted
     * @return a list of file which has been deleted since the last change.
     */
    public List<String> ifFileHasDeleted() {
        ArrayList<String> res = new ArrayList<>();
        for (String fileName : this.mapping.keySet()) {
            File file = Utils.join(CWD, fileName);
            if (!file.exists()) {
                res.add(fileName);
            }
        }
        return res;
    }

    public boolean containsFile(String fileName) {
        return mapping.containsKey(fileName);
    }

    public boolean checkIfSame(String fileName, String hash) {
        return mapping.containsKey(fileName) && Objects.equals(mapping.get(fileName), hash);
    }

    public String getFileHashIfExist(String fileName) {
        return mapping.get(fileName);
    }

    @Deprecated // BC the need of compare the file and stageFile, removedFIle
    public void resetFileOnTheCommitToWorkDirectory() {
        // get the diff file from commit
        List<String> changeFile = ifFileHasChange();

        for (String fileName: changeFile) {
            File origin = Utils.join(REPO, BLOB_FOLDER, getFileHashIfExist(fileName));
            File destination = Utils.join(CWD, fileName);
            try {
                copyFile(origin, destination);
            } catch (IOException e) {
                log.error("IOException");
            }
        }
    }

    public void addKeyValueMapping(String fileName, String hash) {
        this.mapping.put(fileName, hash);
    }

    public void removeKeyValueMapping(String fileName, String hash) { this.mapping.remove(fileName, hash); }

    public boolean checkIfKeyExistInMapping(String fileName) {
        return mapping.containsKey(fileName);
    }

    // TODO only using for debug: remove !!!
    public HashMap<String, String> getMapping() {
        return mapping;
    }

    public String toString() {
        return String.format(
                """
                ===
                commit %s
                Date: %s
                %s
                
                """,
                Utils.sha1(Utils.serialize(this)),
                String.format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", date.getTime()),
                logMessage
        );
    }
}

