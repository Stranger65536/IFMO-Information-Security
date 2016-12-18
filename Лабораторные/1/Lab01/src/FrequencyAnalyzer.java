import exceptions.CipherHappensException;
import exceptions.DuplicatedCharacterInAlphabetException;
import exceptions.EmptyAlphabetException;
import exceptions.NoSuchSymbolException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author vladislav.trofimov@emc.com
 */
class FrequencyAnalyzer {

    private static char[] getCharacters(ArrayList<CharacterStatistic> characterStatistics) {

        char[] result = new char[characterStatistics.size()];

        for (int i = 0; i < characterStatistics.size(); i++) {
            result[i] = characterStatistics.get(i).getCharacter();
        }

        return result;
    }

    public static char[] getSortedFrequencyStatisticFromFile(final String inputFilePath, final String encoding, final char[] alphabet) throws CipherHappensException, IOException {

        if (alphabet == null || alphabet.length == 0) {
            throw new EmptyAlphabetException();
        }

        if (SubstitutionKey.hasDuplications(alphabet)) {
            throw new DuplicatedCharacterInAlphabetException();
        }

        ArrayList<CharacterStatistic> statistics = new ArrayList<>(alphabet.length);

        for (char c : alphabet) {
            statistics.add(new CharacterStatistic(c));
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), encoding));

        while (in.ready()) {
            char symbol = (char) in.read();

            int position = statistics.indexOf(new CharacterStatistic(symbol));

            if (position != -1) {
                statistics.get(position).increase();
            }
        }

        Collections.sort(statistics, new Comparator<CharacterStatistic>() {
            @Override
            public int compare(CharacterStatistic o1, CharacterStatistic o2) {
                return o2.compare(o1);
            }
        });

        return getCharacters(statistics);
    }
}
