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
import java.io.Serializable;

public class Helper {
    static final Logger log = Logger.INSTANCE;
    static final String BLOB_FOLDER = ".blob";
    static final String BRANCH_FOLDER = ".branch";
    static final String COMMIT_FOLDER = ".commit";
    static final String CACHE_FOLDER = ".cache";
    static final String LOG_FILE = "log";
    static final String HEAD_FILE = "HEAD";
    static final File CWD = new File(System.getProperty("user.dir"));
    static final File REPO = Utils.join(CWD, Main.REPO_NAME);
    static final String[] fileName = new String[] {
            LOG_FILE
    };
    static final String[] directoryName = new String[] {
            BLOB_FOLDER,
            BRANCH_FOLDER,
            COMMIT_FOLDER,
            CACHE_FOLDER,
    };

    public static void initRepository() {
        boolean flag = true;
        flag = REPO.mkdir() && flag;

        // create necessary
        for (String name: fileName) {
            File file = Utils.join(REPO, name);
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("IOException when create file %s", file);
                e.printStackTrace();
            }
        }

        // create directory
        for (String str: directoryName) {
            File file = Utils.join(REPO, str);
            flag = file.mkdir() && flag;
            log.trace("complete the create of %s", file);
        }
        assert flag;

        // add branch
        Branch branch = new Branch();   // create branch with default name and initial commit
        File head = Utils.join(REPO, CACHE_FOLDER, HEAD_FILE);
        // create and save the branch in the HEAD file
        saveContentInFile(head, branch);
        log.debug("\n\n[current branch]: \n%s", branch);

        log.debug("the init repository has %s", flag? "complete": "failure");
    }

    public static void saveContentInFile(File file, Serializable data) {
        // if repetition then overwrite else create
        if (file.exists() && file.isFile()) {
            // overwrite data
            Utils.writeObject(file, data);
        } else {
            // create file and write the content
            assert !file.exists();
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("IOException when create file %s", file);
            }
            assert file.exists();
            Utils.writeObject(file, data);
        }
    }

    public void addContentIntoLog(String message) {
        File logFile = Utils.join(REPO, CACHE_FOLDER, LOG_FILE);
        assert logFile.exists();
        
    }

}
