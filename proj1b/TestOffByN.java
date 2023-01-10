
/*
 * @File:   TestOffByN.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 下午1:11
 * @Version:0.0
 */

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {
    static Palindrome palindrome = new Palindrome();

    CharacterComparator offBy3 = new OffByN(3);
    CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testOffBy3() {
        assertTrue(offBy3.equalChars('a', 'd'));
        assertFalse(offBy3.equalChars('a', 'c'));
    }

    @Test
    public void testOffBy5() {
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('z', 'x'));
    }

    @Test
    public void basicTest() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
    }
}
