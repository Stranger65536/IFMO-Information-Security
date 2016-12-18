import exceptions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vladislav.trofimov@emc.com
 */
class SubstitutionKey {

    private char[] originalAlphabet;
    private char[] encodedAlphabet;

    public SubstitutionKey(final char[] originalAlphabet, final char[] encodedAlphabet) throws CipherHappensException {

        if (originalAlphabet == null || encodedAlphabet == null || originalAlphabet.length == 0 || encodedAlphabet.length == 0) {
            throw new EmptyAlphabetException();
        }

        if (originalAlphabet.length != encodedAlphabet.length) {
            throw new MismatchAlphabetsLengthException();
        }

        if (hasDuplications(originalAlphabet) || hasDuplications(encodedAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException();
        }

        int length = originalAlphabet.length;
        this.originalAlphabet = Arrays.copyOf(originalAlphabet, length);
        this.encodedAlphabet = Arrays.copyOf(encodedAlphabet, length);
    }

    public SubstitutionKey(final char[] originalAlphabet, final int key) throws CipherHappensException {

        if (originalAlphabet == null || originalAlphabet.length == 0 ) {
            throw new EmptyAlphabetException();
        }

        if (hasDuplications(originalAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException();
        }

        int length = originalAlphabet.length;
        this.originalAlphabet = Arrays.copyOf(originalAlphabet, length);
        this.encodedAlphabet = Permutator.getCaesarPermutation(originalAlphabet, key);
    }

    static boolean hasDuplications(final char[] array) throws EmptyAlphabetException {

        if (array == null || array.length == 0) {
            throw new EmptyAlphabetException();
        }

        Set<Character> bucket = new HashSet<>();

        for (char i : array) {
            if (bucket.contains(i)) return true;
            bucket.add(i);
        }

        return false;
    }

    public static int getCharPosition(final char[] alphabet, char key) throws EmptyAlphabetException {

        if (alphabet == null || alphabet.length == 0) {
            throw new EmptyAlphabetException();
        }

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == key) {
                return i;
            }
        }

        return -1;
    }

    public char getOriginalKeyByEncoded(char encodedKey) throws CipherHappensException {

        if (hasDuplications(encodedAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException(" " + encodedKey);
        }

        int index = getCharPosition(encodedAlphabet, encodedKey);

        if (index != -1) {
            return originalAlphabet[index];
        } else {
            throw new NoSuchSymbolException(" " + encodedKey);
        }
    }

    public char getEncodedKeyByOriginal(char originalKey) throws CipherHappensException {

        if (hasDuplications(encodedAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException(" " + originalKey);
        }

        int index = getCharPosition(originalAlphabet, originalKey);

        if (index != -1) {
            return encodedAlphabet[index];
        } else {
            throw new NoSuchSymbolException(" " + originalKey);
        }
    }

    public void definePair(char originalSymbol, char encodedSymbol) throws CipherHappensException {

        int pos1 = getCharPosition(originalAlphabet, originalSymbol);
        int pos2 = getCharPosition(encodedAlphabet, encodedSymbol);

        if (pos1 == -1 || pos2 == -1) {
            throw new NoSuchSymbolException("original: " + originalSymbol + " encoded: " + encodedSymbol);
        }

        char swap = encodedAlphabet[pos1];
        encodedAlphabet[pos1] = encodedAlphabet[pos2];
        encodedAlphabet[pos2] = swap;
    }

    public char[] getEncodedAlphabet() throws CipherHappensException {

        if (hasDuplications(encodedAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException();
        }

        return encodedAlphabet;
    }

    public char[] getOriginalAlphabet() throws CipherHappensException {

        if (hasDuplications(originalAlphabet)) {
            throw new DuplicatedCharacterInAlphabetException();
        }

        return originalAlphabet;
    }

    public int length() {
        return originalAlphabet.length;
    }
}
