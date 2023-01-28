package gitlet;
/*
 * @File:   Branch.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/18 下午9:20
 * @Version:0.0
 */

import java.io.Serializable;

import static gitlet.Helper.getCurrent;
import static gitlet.Helper.log;

public class Branch implements Serializable {
    static final String DEFAULT_REPO_NAME = "master";
    String name;
    Commit commit;

    public Branch() {
        this.name = DEFAULT_REPO_NAME;
        this.commit = new Commit(true);
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
}
