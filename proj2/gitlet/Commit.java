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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static gitlet.Helper.*;

public class Commit implements Serializable {
    static transient File commitFolder = Utils.join(REPO, Helper.COMMIT_FOLDER);
//    static transient File log = Utils.join(Helper.REPO, Helper.LOG_FILE);
    String logMessage;
    Timestamp timestamp;
    HashMap<String, String> mapping;
    Commit prev;

    public Commit(boolean logFlag) {
        // will only been call once (by the initial commit)
        logMessage = "initial commit";
        timestamp = new Timestamp(0);
        mapping = new HashMap<>();
        prev = null;
        if (logFlag) log();
    }

    public Commit(String message, boolean logFlag) {
        logMessage = message;
        timestamp = new Timestamp(System.currentTimeMillis());
        // TODO finish commit mapping (using copy and add or delete from zone)
        mapping = new HashMap<>();
        prev = null;
        if (logFlag) log();
    }

    public static Commit appendCommit(Commit commit, boolean logFlag, String message) {
        Commit newCommit = new Commit(message, false);
        newCommit.prev = commit;
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

    public List<String> ifFileHasChange() {
        ArrayList<String> res = new ArrayList<>();
        for (String name: mapping.keySet()) {
            File file = Utils.join(REPO, BLOB_FOLDER, name);
            if (Objects.equals(mapping.get(name), Utils.sha1(Utils.serialize(file)))) {
                res.add(name);
            }
        }
        log.debug("ifFileHasChange: %s", res.toString());
        return res;
    }

    public boolean containsFile(String fileName) {
        return mapping.containsKey(fileName);
    }

    public boolean checkIfSame(String fileName, String hash) {
        return mapping.containsKey(fileName) && Objects.equals(mapping.get(fileName), hash);
    }

    public String toString() {
        return String.format(
                """
                ===
                commit %s
                Date: %s
                %s
                """,
                Utils.sha1((Object) Utils.serialize(this)),
                timestamp.toString(),
                logMessage
        );
    }
}

