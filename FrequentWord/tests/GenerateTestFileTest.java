import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.io.File;

/**
 * Test for
 * @see GenerateTestFile
 */
public class GenerateTestFileTest {
    private static final String FILE_PATH = "in.txt";
    private static final String SEPARATOR = "|";
    private static final int NUMBER_WORDS_IN_LINE = 40;

    /**
     * Size of genered test.
     * It actually supposed to be bigger than specified size<br>
     *     because of separators and system.<br>
     *         This is fine because we need a huge file for testing.
     * @throws Exception
     */
    @Test
    public void generFile() throws Exception {
        long mFileSize = 1000000L; //1 megabyte

        GenerateTestFile tf = new GenerateTestFile(mFileSize, SEPARATOR, NUMBER_WORDS_IN_LINE);
        tf.generFile(FILE_PATH);

        File file = new File(FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > mFileSize);

        mFileSize = 10000000000L; //10 gigabytes
        tf.setSize(mFileSize);
        tf.generFile(FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > mFileSize);
    }

}