
/*
 * @File:   NBody.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/6 下午7:25
 * @Version:0.0
 */


public class NBody {
    public static double readRadius(String str) {
        In in = new In(str);
        in.readInt();
        return in.readDouble();
    }

    
}