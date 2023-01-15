package capers;
/*
 * @File:   test.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/15 下午4:18
 * @Version:0.0
 */

import java.io.File;
import java.io.IOException;

public class test {
    public static File CWD = new File(System.getProperty("user.dir"));
    public static void main(String[] args) throws IOException {
        System.out.println("simple file operation");
        System.out.println(CWD);

        System.out.println(CWD);
        File newDict = Utils.join(CWD, ".capers");
        System.out.println(newDict);
        System.out.println(newDict.exists());

        System.out.printf("the output of mkdir: %s", newDict.mkdir());

        System.out.println(newDict.exists());

        File newFile = Utils.join(CWD, ".capers", "story");
        System.out.println(newFile.exists());
        newFile.createNewFile();
        System.out.println(newFile.exists());

    }
}
