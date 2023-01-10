import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    static CharacterComparator offByOne = new OffByOne();
    static Palindrome palindrome = new Palindrome();
//    static OffByOne obo = new OffByOne();
    @Test
    public void ifEqualCharsWork() {
        assertTrue(offByOne.equalChars('v', 'w'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));

        assertFalse(offByOne.equalChars('b', 'b'));
        assertFalse(offByOne.equalChars('e', '9'));
    }

    static OffByOne obo = new OffByOne();

    @Test
    public void example() {
        assertTrue(palindrome.isPalindrome("abb", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));

        assertFalse(palindrome.isPalindrome("apple", obo));
        assertFalse(palindrome.isPalindrome("banana", obo));
    }

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('z', 'y'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('B', 'A'));
        assertTrue(offByOne.equalChars('1', '2'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('@', 'A'));
        assertTrue(offByOne.equalChars('`', 'a'));
        assertTrue(offByOne.equalChars('z', '{'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('A', 'a'));
        assertFalse(offByOne.equalChars('A', 'C'));
        assertFalse(offByOne.equalChars('c', '3'));
        assertFalse(offByOne.equalChars('x', 'z'));
        assertFalse(offByOne.equalChars('Z', 'X'));
        assertFalse(offByOne.equalChars('1', '3'));
        assertFalse(offByOne.equalChars('?', '!'));
        assertFalse(offByOne.equalChars('`', 'A'));
        assertFalse(offByOne.equalChars(' ', '"'));
    }


}
