import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bonar on 3/4/2017.
 */
public class ComplementaryPairsTest {
    @Test
    public void findComplementaryPairs() throws Exception {
        ComplementaryPairs complementaryPairs = new ComplementaryPairs();

        {
            int[] array = {1, 2, 3, -1, 4, 5, -7};
            int k = 3;
            Integer[][] expected = {{1, 2}, {2, 1}, {-1, 4}, {4, -1}};
            assertEquals(expected, complementaryPairs.findComplementaryPairs(array, k));
        }

        {
            int[] array = {1, 2, 3};
            int k = 1;
            Integer[][] expected = {};
            assertEquals(expected, complementaryPairs.findComplementaryPairs(array, k));
        }

        {
            int[] array = {5, 1, 4, 9, -100, 0, -3, 8};
            int k = 6;
            Integer[][] expected = {{5, 1}, {1, 5}, {9, -3}, {-3, 9}};
            assertEquals(expected, complementaryPairs.findComplementaryPairs(array, k));
        }

        {
            int[] array = {5, 5, 10, -10, 0, 9, 8, -1, 1};
            int k = 10;
            Integer[][] expected = {{5, 5}, {5, 5}, {10, 0}, {0, 10}, {9, 1}, {1, 9}};
            assertEquals(expected, complementaryPairs.findComplementaryPairs(array, k));
        }
    }

}