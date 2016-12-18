import exceptions.CipherHappensException;
import exceptions.EmptyAlphabetException;

import java.io.*;
import java.util.ArrayList;

/**
 * @author vladislav.trofimov@emc.com
 */
class FileEncoder {

    public static void encodeFileByKey(final String inputFileName, final String inputFileEncoding,
                                       final String outputFileName, final String outputFileEncoding,
                                       final SubstitutionKey decodeData) throws CipherHappensException, IOException {

        if (decodeData == null) {
            throw new EmptyAlphabetException("No alphabet entered");
        }

        ArrayList<Character> originalCharacters = new ArrayList<>(decodeData.length());

        for (char c : decodeData.getOriginalAlphabet()) {
            originalCharacters.add(c);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), inputFileEncoding));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), outputFileEncoding));

        while (in.ready()) {

            char c = (char) in.read();

            if (originalCharacters.contains(c)) {
                out.append(decodeData.getEncodedKeyByOriginal(c));
            } else {
                out.append(c);
            }
        }

        out.close();
        in.close();
    }
}
