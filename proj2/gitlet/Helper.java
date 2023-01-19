package gitlet;
/*
 * @File:   Helper.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/19 下午3:17
 * @Version:0.0
 */

import java.io.File;

public class Helper {
    static final Logger log = Logger.INSTANCE;
    static final File CWD = new File(System.getProperty("user.dir"));
    static final String[] directoryName = new String[]{
            ".blob", ".branch", ".commit", ".cache"
    };

    public static void initRepository() {
        File gitletRepo = Utils.join(CWD, Main.REPO_NAME);
        boolean flag = true;
        flag = gitletRepo.mkdir() && flag;
        for (String str: directoryName) {
            File file = Utils.join(gitletRepo, str);
            flag = file.mkdir() && flag;
            log.trace("complete the create of %s", file);
        }
        assert flag;
        log.debug("the init repository has %s", flag? "complete": "failure");
    }



}
