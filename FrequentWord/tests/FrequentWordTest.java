import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;

/**
 * Test for
 * @see FrequentWord
 */
public class FrequentWordTest {
    private static final String REGEX_FILTER = "\\|";

    @Test
    public void findFrequentWords() throws Exception
    {
        //Lets test it on a small file.
        //So we know the result.
        /*
        in.txt:
            apple|link|apple|link|candy|oreo|person|oreo|orange|home
            link|oreo|home|homeless|hummels|gregory|home|oreo|apple|candy
            apple|link|apple|link|candy|oreo|person|oreo|orange|home
            link|oreo|home|homeless|hummels|gregory|home|oreo|apple|candy
         */
        //We want to find 4 most frequent used words.
        /*
        Estimated result:
            oreo 8
            apple 6
            link 6
            home 6
         */

        int mNumberWords = 4;
        String mInputFilePath = "in.txt";
        String mOutputFilePath = "out.txt";
        String[] mEstimatedResult = {"oreo 8", "apple 6", "link 6", "home 6"};
        FrequentWord frequentWord = new FrequentWord();
        frequentWord.findFrequentWords(mInputFilePath, mOutputFilePath, REGEX_FILTER, mNumberWords);
        compareResult(mEstimatedResult, mOutputFilePath);

        //Now lets just test if algorithm can handle a huge file >10 GB.
        //So size of input.txt is 11,928,565,246 bytes
        mNumberWords = 100000;
        mInputFilePath = "input.txt";
        mOutputFilePath = "output.txt";
        frequentWord.findFrequentWords(mInputFilePath, mOutputFilePath, REGEX_FILTER, mNumberWords);
        File file = new File(mOutputFilePath);
        assertTrue(file.exists());

    }

    private void compareResult(String[] mEstimatedResult, String mFilePath)
    {
        File file = new File(mFilePath);
        assertTrue(file.exists());

        FileInputStream fis;
        BufferedReader br;

        try {
            fis = new FileInputStream(mFilePath);
            br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                assertTrue(line.equals(mEstimatedResult[i]));
                i++;
            }
            assertTrue(i == mEstimatedResult.length);
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
        }

    }
}