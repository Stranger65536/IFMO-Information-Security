import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
class Permutator {

    private static ArrayList<int[]> numberPermutations;

    public static ArrayList<char[]> generateNthPermutations(final char[] source, final int countOfPermutableElements) {

        ArrayList<char[]> permutations = new ArrayList<>(factorial(countOfPermutableElements));

        numberPermutations = new ArrayList<>(factorial(countOfPermutableElements));
        generateAllPermutations(0, countOfPermutableElements, new int[source.length], new int[source.length]);

        for (int[] mask : numberPermutations) {
            permutations.add(permuteByMask(Arrays.copyOf(source, source.length), mask));
        }

        return permutations;
    }

    public static ArrayList<char[]> generateCaesarPermutations(final char[] statistic, final char[] alphabet, int countOfPermutableElements) {

        ArrayList<char[]> permutations = new ArrayList<>(countOfPermutableElements);
        countOfPermutableElements = countOfPermutableElements % (statistic.length + 1);

        for (int i = 1; i <= countOfPermutableElements; i++) {

            ArrayList<Character> alphabetList = new ArrayList<>(alphabet.length);
            for (char c: alphabet) {
                alphabetList.add(c);
            }

            int pos = alphabetList.indexOf(statistic[i]);

            char[] permutation = getCaesarPermutation(alphabet, pos);

            permutations.add(permutation);
        }

        return permutations;
    }

    public static char[] getCaesarPermutation(final char[] alphabet, int key) {

        key = key % alphabet.length;

        char[] permutation = new char[alphabet.length];

        for (int j = 0; j < alphabet.length; j++) {
            permutation[j] = alphabet[(key + j) % alphabet.length];
        }

        return permutation;
    }

    private static void generateAllPermutations(final int x, final int n, final int[] b, final int[] a) {

        if (x == n) {

            int[] permutation = new int[n];
            for (int i = 0; i < n; i++) {
                permutation[i] = a[i] - 1;
            }
            numberPermutations.add(permutation);

        } else {

            for (int i = 0; i < n; i++) {
                if (b[i] == 0) {
                    b[i] = 1;
                    a[x] = i + 1;
                    generateAllPermutations(x + 1, n, b, a);
                    b[i] = 0;
                }
            }
        }
    }

    private static char[] permuteByMask(char[] source, final int[] mask) {

        char[] subArray = Arrays.copyOfRange(source, 0, mask.length);

        for (int i = 0; i < mask.length; i++) {
            source[i] = subArray[mask[i]];
        }

        return source;
    }


    private static int factorial(int n) {

        int fact = 1;

        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        return fact;
    }
}
