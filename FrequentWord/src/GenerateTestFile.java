import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * <p>A class for generating file with specified size.</p>
 * <p>This class is just to get a huge size test file with specific structure.</p>
 * <p>Structure: <i>word</i> <i>separator</i></p>
 * <p>This generator generates words like integer for easier generating. Remember, its just for testing.</p>
 */
public class GenerateTestFile {
    private static final int MAX_VALUE = 200000;
    private static final int MIN_VALUE = 0;

    private long mSize;
    private String mSeparator;
    private int mNumberWordsInLIne;

    /**
     *  Constructor
     * @param mSize size of file.
     * @param mSeparator separator.
     * @param mNumberWordsInLIne number items in 1 line.
     */
    public GenerateTestFile(long mSize, String mSeparator, int mNumberWordsInLIne) {
        this.mSize = mSize;
        this.mSeparator = mSeparator;
        this.mNumberWordsInLIne = mNumberWordsInLIne;
    }

    /**
     *
     * @return get size of file.
     */
    public long getSize() {
        return mSize;
    }

    /**
     *
     * @param mSize set size of file.
     */
    public void setSize(long mSize) {
        this.mSize = mSize;
    }

    /**
     *
     * @return get separator.
     */
    public String getSeparator() {
        return mSeparator;
    }

    /**
     *
     * @param mSeparator set separator.
     */
    public void setSeparator(String mSeparator) {
        this.mSeparator = mSeparator;
    }

    /**
     *
     * @return get number words in 1 line.
     */
    public int getNumberWordsInLIne() {
        return mNumberWordsInLIne;
    }

    /**
     *
     * @param mNumberWordsInLIne set number words in 1 line.
     */
    public void setNumberWordsInLIne(int mNumberWordsInLIne) {
        this.mNumberWordsInLIne = mNumberWordsInLIne;
    }

    /**
     * Generate text file.
     * @param mFilePath file path.
     */
    public void generFile(String mFilePath)
    {
        long currentSize = 0;
        Random rand = new Random();

        try {
            PrintWriter pw = new PrintWriter(mFilePath);

            while(currentSize < mSize)
            {
                String line = "";
                String tmp;
                for(int i = 0; i < mNumberWordsInLIne; i++)
                {
                    tmp = Integer.toString(rand.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE);
                    line += tmp + mSeparator;

                    currentSize += tmp.length();
                }
                pw.println(line);
            }

            pw.close();
        } catch (FileNotFoundException e){
        }
    }
}
