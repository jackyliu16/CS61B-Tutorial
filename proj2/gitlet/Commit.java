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
import java.util.HashMap;

public class Commit implements Serializable {
    static transient File commitFolder = Utils.join(Helper.REPO, Helper.COMMIT_FOLDER);
    static transient File log = Utils.join(Helper.REPO, Helper.LOG_FILE);
    String logMessage;
    Timestamp timestamp;
    HashMap<String, File> mapping;
    Commit prev;

    public Commit() {
        // will only been call once (by the initial commit)
        logMessage = "initial commit";
        timestamp = new Timestamp(0);
        mapping = new HashMap<>();
        prev = null;
        log();
    }

    public Commit(String message) {
        logMessage = message;
        timestamp = new Timestamp(System.currentTimeMillis());
        // TODO finish commit mapping (using copy and add or delete from zone)
        mapping = new HashMap<>();
        prev = null;
        log();
    }

    public Commit(Commit commit, String message) {
        Commit newCommit = new Commit(message);
        newCommit.prev = commit;
    }

    private void log() {
        // add info into log file
        assert log.exists();
        Utils.writeContents(log, toString());

        // save commit node in the file
        saveCommitInFile();
    }

    private void saveCommitInFile() {
        String hash = Utils.sha1((Object) Utils.serialize(this));
        File file = Utils.join(commitFolder, hash);
//      it seems base on writeContent function that it will create file if need
//        if (Utils.plainFilenamesIn(commitFolder).contains(hash)) {
//            // overwrite data
//        }
        Utils.writeObject(file, this);
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

