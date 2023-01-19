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
import java.sql.Timestamp;
import java.util.HashMap;

public class Commit {
    String logMessage;
    Timestamp timestamp;
    HashMap<String, File> mapping;
    Commit prev;

    public Commit() {
        logMessage = "initial commit";
        timestamp = new Timestamp(0);
        mapping = new HashMap<>();
        prev = null;
    }

    public Commit(String message) {
        logMessage = message;
        timestamp = new Timestamp(System.currentTimeMillis());
        // TODO finish commit mapping (using copy and add or delete from zone)
        mapping = new HashMap<>();
        prev = null;
    }

    public Commit(Commit commit, String message) {
        Commit newCommit = new Commit(message);
        newCommit.prev = commit;
    }
}

