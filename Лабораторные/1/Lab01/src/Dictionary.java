import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author vladislav.trofimov@emc.com
 */
class Dictionary {

    private final HashMap<Integer, HashSet<String>> dictionary;

    public Dictionary(final String dictionaryPath, final String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        this.dictionary = readDictionary(dictionaryPath, encoding);
    }

    private static HashMap<Integer, HashSet<String>> readDictionary(final String dictionaryPath, final String encoding) throws FileNotFoundException, UnsupportedEncodingException {

        HashMap<Integer, HashSet<String>> dictionary = new HashMap<>(50);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryPath), encoding))) {
            while (in.ready()) {
                String word = convertToLessCharacterString(in.readLine());
                if (dictionary.containsKey(word.length())) {
                    dictionary.get(word.length()).add(word);
                } else {
                    HashSet<String> hashSet = new HashSet<>(100000);
                    hashSet.add(word);
                    dictionary.put(word.length(), hashSet);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return dictionary;
    }

    //TODO hardcode bad practice; add substitution pairs as parameter
    private static String convertToLessCharacterString(final String source) {

        if (source == null) {
            throw new NullPointerException();
        }

        StringBuilder stringBuilder = new StringBuilder(6);

        for (char c : source.toCharArray()) {
            if (c == 'ё') {
                stringBuilder.append('е');
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    public boolean containsWord(final String word) {
        return dictionary.containsKey(word.length()) && dictionary.get(word.length()).contains(word);
    }

    public int size() {
        int res = 0;
        for (HashSet<String> hashSet : dictionary.values()) {
            res = hashSet.size();
        }
        return res;
    }
}
