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

public class Branch implements Serializable {
    static final String DEFAULT_REPO_NAME = "master";
    String name;
    Commit commit;

    public Branch() {
        this.name = DEFAULT_REPO_NAME;
        this.commit = new Commit(true);
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
}
