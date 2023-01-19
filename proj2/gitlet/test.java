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

public class test {
    static final File CWD = new File(System.getProperty("user.dir"));
    public static void main(String[] args) throws IOException {
        File testDirectory = Utils.join(CWD, ".test");
        if (!Utils.join(CWD, ".test").exists()) {
            testDirectory.mkdir();
        }
        File test1 = Utils.join(testDirectory, "file1.txt");
        test1.createNewFile();
        byte[] bytes = Utils.serialize(test1);
        System.out.println(bytes);
        System.out.println(Utils.sha1(bytes));
        System.out.println(Utils.sha1(bytes));
        System.out.println(Utils.sha1(bytes));
    }
}
