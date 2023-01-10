
/*
 * @File:   OffByN.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 下午1:08
 * @Version:0.0
 */

public class OffByN implements CharacterComparator {
    private int offset;
    public OffByN(int N) {
        offset = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == offset;
    }
}
