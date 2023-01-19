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
import java.io.IOException;

public class Helper {
    static final String BLOB_FOLDER = ".blob";
    static final String BRANCH_FOLDER = ".branch";
    static final String COMMIT_FOLDER = ".commit";
    static final String CACHE_FOLDER = ".cache";
    static final Logger log = Logger.INSTANCE;
    static final File CWD = new File(System.getProperty("user.dir"));
    static final String[] directoryName = new String[]{
            BLOB_FOLDER,
            BRANCH_FOLDER,
            COMMIT_FOLDER,
            CACHE_FOLDER,
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

        // TODO add branch


        // TODO add initial commit

    }

    public static void saveBranch(Branch branch) {
        // the file name is the branch name
        File branchFile = Utils.join(CWD, Main.REPO_NAME, BRANCH_FOLDER, branch.getName());
        log.trace("save branch in %s", branchFile);
        if (!branchFile.exists()) {
            // if not exist thus create a new file
            try {
                branchFile.createNewFile();
            } catch (IOException e) {
                log.error("IOException: when create file (%s)", branchFile);
                e.printStackTrace();
            }
        }
        Utils.writeObject(branchFile, branch);
    }
    public static void saveCommit(Commit commit) {
        byte[] bytes = Utils.serialize(commit);
        File commitFolder = Utils.join(CWD, Main.REPO_NAME, COMMIT_FOLDER);
        log.trace("open commit folder");
        assert commitFolder.exists();
        // the commit shouldn't exist before, otherwise it will just pass
        if (Utils.plainFilenamesIn(commitFolder).contains(bytes)) {
            log.debug("commit %7s has exist before save", bytes.toString().substring(0, 7));
            return;
        } else {
            File commitFile = Utils.join(commitFolder, bytes.toString());
            log.trace("save commit in %s", commitFile);
            try {
                commitFile.createNewFile();
            } catch (IOException e) {
                log.error("IOException: when create file (%s)", commitFile);
                e.printStackTrace();
            }
            Utils.writeContents(commitFile, bytes);
        }
    }
}
