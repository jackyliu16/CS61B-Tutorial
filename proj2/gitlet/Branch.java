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

    public Branch() {
        this.name = DEFAULT_REPO_NAME;
        this.commit = new Commit(true);
    }

    public Branch(String name, Commit commit) {
        this.name = name;
        this.commit = commit;
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
        // create a branch
        Branch newBranch = new Branch(branchName, this.commit);
        File branchFile = Utils.join(REPO, BRANCH_FOLDER, branchName);
        // save the branch
        Helper.saveContentInFile(branchFile, Utils.serialize(newBranch));
    }
}
