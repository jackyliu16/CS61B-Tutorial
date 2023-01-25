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
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Test {
    static final Logger log = Logger.INSTANCE;
    static final File CWD = new File(System.getProperty("user.dir"));
    static final File REPO = Utils.join(CWD, Main.REPO_NAME);
    static final File TEST_FOLDER = Utils.join(CWD, "test");
    public static void main(String[] args) throws IOException {
        log.setLogLevel(LogLevel.Trace);

    }
}

/* example of
        File file = Utils.join(CWD, "a");
        Node node = new Node();
        Utils.writeObject(file, node);
        System.out.println(node);

        byte[] bytes = Utils.serialize(node);
//        System.out.println(Arrays.toString(bytes));
        System.out.println(Utils.sha1(bytes));                      // 5d08724a7f6c6ec558ad76f98bec9152afb00a1f

        System.out.println("===== start reading =====");

        Node newNode = Utils.readObject(file, Node.class);
        System.out.println(newNode);
        System.out.println(Utils.sha1(Utils.serialize(newNode)));   // 5d08724a7f6c6ec558ad76f98bec9152afb00a1f

        System.out.println("===== write info =====");
        Node node2 = new Node("apple", 25);
//        File file2 = Utils.join(CWD, "a");
        Utils.writeObject(file, node2);

        byte[] bytes2 = Utils.serialize(node2);
//        System.out.println(Arrays.toString(bytes));
        System.out.println(Utils.sha1(bytes2));                     // dfa9dd64428bb9581214485f710c8418b6f381f0

        Node newNode2 = Utils.readObject(file, Node.class);
        System.out.println(newNode);
        System.out.println(Utils.sha1(Utils.serialize(newNode2)));  // dfa9dd64428bb9581214485f710c8418b6f381f0
 */

class Node implements Serializable {
    String str;
    int aNum;
    Node() {
        str = "nothing";
        aNum = 0;
    }

    Node(String a, int b) {
        str = a;
        aNum = b;
    }

    public String toString() {
        return String.format(
                """
                str: %s
                aNum: %d
                """, str, aNum);
    }
}
