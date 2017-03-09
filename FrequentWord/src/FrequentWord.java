import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for finding top most frequently used words.
 */
public class FrequentWord {

    /**
     * Sort Map by descending value.
     * @param map map.
     * @param <K> key.
     * @param <V> value.
     * @return sorted map.
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Read file line by line.
     * @param mFileName file path.
     * @param mRegex regex filter.
     * @return read map.
     */
    private Map<String, Integer> readFile(String mFileName, String mRegex)
    {
        Map<String, Integer> result = new HashMap<>();

        FileInputStream fis;
        BufferedReader br;

        try {
            fis = new FileInputStream(mFileName);
            br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(mRegex);
                for(String word : words) {
                    try {
                        result.put(word, result.get(word) + 1);
                    } catch (NullPointerException e) {
                        result.put(word, 1);
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
        }

        return result;
    }

    /**
     * Write top frequent words to file.
     * @param map map.
     * @param mFilePath file path.
     * @param number number of words.
     */
    private void writeToFile(Map<String, Integer> map, String mFilePath, int number)
    {
        try {
            PrintWriter pw = new PrintWriter(mFilePath);
            int i = 0;

            for(Map.Entry<String, Integer> entry : map.entrySet()) {
                i++;
                pw.println(entry.getKey() + " " + entry.getValue());
                if(i >= number)
                    break;
            }
            pw.close();
        } catch (FileNotFoundException e){
        }
    }

    /**
     * Find most frequent words in file.
     * @param mFileName file path.
     * @param mResultFile result file path.
     * @param mRegex regex filter. ("\\|" for "|" separator).
     * @param number number of words.
     */
    public void findFrequentWords(String mFileName, String mResultFile, String mRegex, int number)
    {
        Map<String, Integer> mDictionary;

        mDictionary = readFile(mFileName, mRegex);

        mDictionary = sortByValue(mDictionary);

        writeToFile(mDictionary, mResultFile, number);
    }
}