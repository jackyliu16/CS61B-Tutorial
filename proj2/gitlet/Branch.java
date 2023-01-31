package gitlet;
/*
 * @File:   Branch.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/18 下午9:20
 * @Version:0.0
 */

import java.io.File;
import java.io.Serializable;

import static gitlet.Helper.*;

public class Branch implements Serializable {
    static final String DEFAULT_REPO_NAME = "master";
    String name;
    Commit commit;
    StringBuilder logInfo;

    public Branch() {
        this.name = DEFAULT_REPO_NAME;
        this.commit = new Commit(false);    // BC the commit will getCurrent
        this.logInfo = new StringBuilder();
        logInfo.insert(0, commit.toString());
    }

    public Branch(String name, Commit commit, StringBuilder sb) {
        this.name = name;
        this.commit = commit;
        this.logInfo = sb;
    }

    public String getName() {
        return this.name;
    }

    public Commit getLatestCommit() {
        return this.commit;
    }

    public String toString() {
        return String.format(
                """
                Name: %s
                Commit: %s
                """,
                name,
                Utils.sha1((Object) Utils.serialize(this.commit))
        );
    }

    public String getFileHash(String fileName) {
        log.trace("%s", fileName);
        if (commit.containsFile(fileName)) {
            return commit.getFileHashIfExist(fileName);
        } else {
            log.debug("the latest commit in branch[%s] not contains [%s]",
                    name,
                    fileName
            );
            return "";
        }
    }

    public void baseOnItCreateANewBranch(String branchName) {
        File branchFile = Utils.join(REPO, CACHE_FOLDER, HEAD_FILE);
        Branch current = Utils.readObject(branchFile, Branch.class);
        Branch newBranch = new Branch(branchName, current.getLatestCommit(), current.getLogInfo());
        File save = Utils.join(REPO, BRANCH_FOLDER, branchName);
        Helper.saveContentInFile(save, newBranch);
    }

    public void appendLogInBegin(String message) {
        logInfo.insert(0, message);
    }

    public StringBuilder getLogInfo() {
        return logInfo;
    }

    public void printLog() {
        System.out.println(logInfo);
    }
}
