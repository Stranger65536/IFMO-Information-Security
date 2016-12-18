import exceptions.CipherHappensException;
import exceptions.EmptyAlphabetException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
class FileDecoder {

    public static int getDecodeQualityByKey(final String inputFileName, final String inputFileEncoding,
                                            final SubstitutionKey decodeData, final Dictionary dictionary, final char[] delimiters) throws IOException, CipherHappensException {

        HashSet<String> matches = new HashSet<>(dictionary.size());

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), inputFileEncoding));

        ArrayList<Character> encodedCharacters = new ArrayList<>(decodeData.length());

        for (char c : decodeData.getEncodedAlphabet()) {
            encodedCharacters.add(c);
        }

        while (in.ready()) {

            String inputFine = in.readLine();
            StringTokenizer st = new StringTokenizer(inputFine, String.valueOf(delimiters));

            while (st.hasMoreTokens()) {

                String encodedWord = st.nextToken();

                StringBuilder stringBuilder = new StringBuilder(encodedWord.length());

                for (char c : encodedWord.toCharArray()) {
                    if (encodedCharacters.contains(c)) {
                        stringBuilder.append(decodeData.getOriginalKeyByEncoded(c));
                    } else {
                        stringBuilder.append(c);
                    }
                }

                String decodedWord = stringBuilder.toString();

                if (dictionary.containsWord(decodedWord)) {
                    matches.add(decodedWord);
                }
            }
        }

        in.close();

        return matches.size();
    }

    public static void decodeFileByKey(final String inputFileName, final String inputFileEncoding,
                                       final String outputFileName, final String outputFileEncoding,
                                       final SubstitutionKey decodeData) throws CipherHappensException, IOException {

        if (decodeData == null) {
            throw new EmptyAlphabetException("No alphabet entered");
        }

        ArrayList<Character> encodedCharacters = new ArrayList<>(decodeData.length());

        for (char c : decodeData.getEncodedAlphabet()) {
            encodedCharacters.add(c);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), inputFileEncoding));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), outputFileEncoding));

        while (in.ready()) {

            char c = (char) in.read();

            if (encodedCharacters.contains(c)) {
                out.append(decodeData.getOriginalKeyByEncoded(c));
            } else {
                out.append(c);
            }
        }

        out.close();
        in.close();
    }
}
