package gitlet;
/*
 * @File:   Helper.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/19 下午3:17
 * @Version:0.0
 */

import java.io.*;

public class Helper {
    static final Logger log = Logger.INSTANCE;
    static final String BLOB_FOLDER = ".blob";
    static final String BRANCH_FOLDER = ".branch";
    static final String COMMIT_FOLDER = ".commit";
    static final String CACHE_FOLDER = ".cache";
    static final String LOG_FILE = "log";
    static final String HEAD_FILE = "HEAD";
    static final String STATUS_FILE = "STATUS";
    static final File CWD = new File(System.getProperty("user.dir"));
    static final File REPO = Utils.join(CWD, Main.REPO_NAME);
    static final String[] fileName = new String[] {
            CACHE_FOLDER + "/" + LOG_FILE,
            CACHE_FOLDER + "/" + STATUS_FILE,
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

        // create directory
        for (String str: directoryName) {
            File file = Utils.join(REPO, str);
            flag = file.mkdir() && flag;
            log.trace("complete the create of %s", file);
        }

        // create necessary
        for (String name: fileName) {
            File file = Utils.join(REPO, name);
            try {
                flag = file.createNewFile() && flag;
                log.trace("complete the create of %s", file);
            } catch (IOException e) {
                log.error("IOException when create file %s", file);
                e.printStackTrace();
            }
            log.trace("%s", flag);
        }
        assert flag;

        // add branch
        Branch branch = new Branch();   // create branch with default name and initial commit
        File head = Utils.join(REPO, CACHE_FOLDER, HEAD_FILE);
        // create and save the branch in the HEAD file
        saveContentInFile(head, branch);
        log.debug("\n\n[current branch]: \n%s", branch);
        // create a StatusController
        StatusController sc = new StatusController();
        saveStatus(sc);

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

    public static void addContentIntoLog(String message) {
        File logFile = Utils.join(REPO, CACHE_FOLDER, LOG_FILE);
        assert logFile.exists();
        Utils.writeContents(logFile, message);
    }

    public static Branch getCurrent() {
        File head = Utils.join(REPO, CACHE_FOLDER, HEAD_FILE);
        // if file not exist -> error
        if (!head.exists()) {
            log.error("head not exist");
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        return Utils.readObject(head, Branch.class);
    }

    public static StatusController getStatus() {
        File head = Utils.join(REPO, CACHE_FOLDER, STATUS_FILE);
        // if file not exist -> error
        if (!head.exists()) {
            log.error("head not exist");
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        return Utils.readObject(head, StatusController.class);
    }

    public static void saveStatus(StatusController sc) {
        File scLocation = Utils.join(REPO, CACHE_FOLDER, STATUS_FILE);
        saveContentInFile(scLocation, sc);
    }

    /** TODO convertAndSaveFile
     * Will Copy the File from work directory to blob directory, with a hash as it's file name
     * NOTE if it's contains a directory that will create a tree node to Save the message
     * @param fileName the file path (relative address) that you want to save to the directory
     */
    void convertAndSaveFile(String fileName) {

    }

    /**
     * copy the file from original path into the destination path
     * @param original the input file
     * @param destination the file direction that we will copy the original to.
     * @return if success
     * @throws IOException None
     * @throws FileNotFoundException None
     */
    static void copyFile(File original, File destination) throws IOException, FileNotFoundException {
        log.debug("Copy Operation: \n\tinput: %s\n\toutput: %s", original, destination);
        // ref: https://www.baeldung.com/java-copy-file
        destination.createNewFile();
        try {
            InputStream in = new BufferedInputStream(
                    new FileInputStream(original)
            );
            OutputStream out = new BufferedOutputStream(
                    new FileOutputStream(destination)
            );
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException");
            throw e;
        } catch (IOException e) {
            log.error("IOException");
            throw e;
        }
    }
}