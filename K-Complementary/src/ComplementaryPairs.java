import java.util.*;

/**
 * Created by bonar on 3/3/2017.
 */

/**
 * Algorithm for findind k-complementary pairs in array
 * @author vb
 */
public class ComplementaryPairs {

    /** Find k-complementary pairs\n
     * Perfomance - O(N)
     * @param arr - initial array
     * @param k - k number to check
     * @return array of complementary pairs
     */
    public Integer[][] findComplementaryPairs(int arr[], int k) {
        Map pairs = new HashMap();

        for (int number : arr) {
            pairs.put(number, k - number);
        }

        List<Integer[]> complementaryPairs = new ArrayList<>();

        for (int number : arr) {
            int complementary = k - number;
            if (pairs.containsKey(complementary)) {
                Integer[] pair = {number, complementary};
                complementaryPairs.add(pair);
            }
        }

        return complementaryPairs.toArray(new Integer[0][0]);
    }
}
