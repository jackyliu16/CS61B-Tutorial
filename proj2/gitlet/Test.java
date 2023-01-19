package gitlet;
/*
 * @File:   test.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/18 下午8:27
 * @Version:0.0
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Test {
    static final File CWD = new File(System.getProperty("user.dir"));
    static final File REPO = Utils.join(CWD, Main.REPO_NAME);
    static final File TEST_FOLDER = Utils.join(CWD, "test");
    public static void main(String[] args) throws IOException {
        TEST_FOLDER.mkdir();
        File file1 = Utils.join(TEST_FOLDER, "file1");
//        file1.createNewFile();
        Utils.writeObject(file1, "object 1");
        Utils.writeObject(file1, "object 2");
        Utils.writeObject(file1, "object 3");

        System.out.println(Arrays.toString(Utils.readContents(file1)));
    }
}
