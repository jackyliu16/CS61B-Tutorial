import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    */

    static Palindrome palindrome = new Palindrome();
    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    // Uncomment this class once you've created your Palindrome class.

    @Test
    public void isPalindrome_empty() {
        assertFalse(palindrome.isPalindrome(""));
    }

    @Test
    public void isPalindrome_normalEven() {
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("ao11oa"));
        assertFalse(palindrome.isPalindrome("abcd"));
        assertFalse(palindrome.isPalindrome("14bd"));
    }

    @Test
    public void isPalindrome_normalOdd() {
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("abcba"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("abcde"));
    }

}
