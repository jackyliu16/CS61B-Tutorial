
/*
 * @File:   OffByONe.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 上午10:13
 * @Version:0.0
 */

public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) <= 1;
    }
}
