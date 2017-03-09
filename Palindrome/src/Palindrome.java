/**
 * Algorithm for checking if string is palindrome
 * @author vb
 */
public class Palindrome {

    /** Check if string is palindrome\n
     * Perfomance - O(N)
     * @param str - string to check
     * @return if string is palindrome
     */
    public boolean isPalindrome(String str)
    {
        int l = 0, r = str.length() - 1;

        while(l < r)
        {
            if(str.charAt(l) != str.charAt(r))
                return false;
            l++;
            r--;
        }

        return true;
    }
}
