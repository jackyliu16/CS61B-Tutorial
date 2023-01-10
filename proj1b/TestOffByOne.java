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
        assertTrue(offByOne.equalChars('v', 'v'));
        assertTrue(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('b', 'a'));

        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('u', 'o'));
    }

    static OffByOne obo = new OffByOne();

    @Test
    public void example() {
        assertTrue(palindrome.isPalindrome("aba", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));

        assertFalse(palindrome.isPalindrome("apple", obo));
        assertFalse(palindrome.isPalindrome("banana", obo));
    }


}
