import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bonar on 3/4/2017.
 */
public class PalindromeTest {
    @Test
    public void isPalindrome() throws Exception {
        Palindrome palindrome = new Palindrome();

        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("1"));
        assertTrue(palindrome.isPalindrome("121"));
        assertTrue(palindrome.isPalindrome("1221"));
        assertTrue(palindrome.isPalindrome("12,.,21"));

        assertFalse(palindrome.isPalindrome("12"));
        assertFalse(palindrome.isPalindrome("12,.,1"));
        assertFalse(palindrome.isPalindrome("1222"));
    }

}