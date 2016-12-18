import exceptions.CipherHappensException;

import java.io.IOException;
import java.util.*;

/**
 * @author vladislav.trofimov@emc.com
 */
class SubstitutionCipher {

    public static final String DECODE_SIMPLE_SUBSTITUTION_PRE_DEFINED_FILE = "-dsp";
    public static final String DECODE_CAESAR_PRE_DEFINED_FILE = "-dcp";
    public static final String DECODE_CAESAR_BY_KEY = "-dck";
    public static final String DECODE_SIMPLE_SUBSTITUTION_BY_KEY = "-dsk";
    public static final String ENCODE_SIMPLE_SUBSTITUTION = "-es";
    public static final String ENCODE_CAESAR = "-ec";
    public static final String INPUT_FILE_OPTION = "-i";
    public static final String INPUT_FILE_ENCODING_OPTION = "-ie";
    public static final String OUTPUT_FILE_OPTION = "-o";
    public static final String OUTPUT_FILE_ENCODING_OPTION = "-oe";
    public static final String ALPHABET_OPTION = "-alpha";
    public static final String KEY_OPTION = "-key";
    public static final String DICTIONARY_OPTION = "-d";
    public static final String DICTIONARY_ENCODING_OPTION = "-de";

    public static void main(String[] args) {
        if (args.length == 0) {

            System.out.println("No parameters entered. Usage: java SubstitutionCipher [command] [options]");
            showHelp();

        } else {

            switch (args[0]) {
                case DECODE_SIMPLE_SUBSTITUTION_PRE_DEFINED_FILE:
                    processDecodeSimpleSubstitutionPreDefinedFile(Arrays.copyOfRange(args, 1, args.length));
                    break;
                case DECODE_CAESAR_PRE_DEFINED_FILE:
                    processDecodeCaesarPreDefinedFile(Arrays.copyOfRange(args, 1, args.length));
                    break;
                case DECODE_SIMPLE_SUBSTITUTION_BY_KEY:
                    processDecodeSimpleSubstitutionByKey(Arrays.copyOfRange(args, 1, args.length));
                    break;
                case DECODE_CAESAR_BY_KEY:
                    processDecodeCaesarByKey(Arrays.copyOfRange(args, 1, args.length));
                    break;
                case ENCODE_SIMPLE_SUBSTITUTION:
                    processEncodeSimpleSubstitution(Arrays.copyOfRange(args, 1, args.length));
                    break;
                case ENCODE_CAESAR:
                    processEncodeCaesar(Arrays.copyOfRange(args, 1, args.length));
                    break;
                default:
                    System.out.println("Invalid command");
                    showHelp();
            }
        }
    }

    private static void processEncodeCaesar(final String[] options) {

        try {
            checkOptionsByKey(options);
            encodeCaesarByKey(options[1], options[3], options[5], options[7], split(options[9]), Integer.parseInt(options[11]));
            System.out.println("Encode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void processEncodeSimpleSubstitution(final String[] options) {

        try {
            checkOptionsByKey(options);
            encodeSimpleSubstitutionByKey(options[1], options[3], options[5], options[7], split(options[9]), split(options[11]));
            System.out.println("Encode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void processDecodeCaesarByKey(final String[] options) {

        try {
            checkOptionsByKey(options);
            decodeCaesarByKey(options[1], options[3], options[5], options[7], split(options[9]), Integer.parseInt(options[11]));
            System.out.println("Decode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void processDecodeSimpleSubstitutionByKey(final String[] options) {

        try {
            checkOptionsByKey(options);
            decodeSimpleSubstitutionByKey(options[1], options[3], options[5], options[7], split(options[9]), split(options[11]));
            System.out.println("Decode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void processDecodeCaesarPreDefinedFile(final String[] options) {

        try {
            checkOptionsDefined(options);
            decodeCaesarPreDefinedFile(options[1], options[3], options[5], options[7], options[9], options[11], Constants.ALPHABET, Constants.DELIMITERS);
            System.out.println("Decode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void processDecodeSimpleSubstitutionPreDefinedFile(final String[] options) {

        try {
            checkOptionsDefined(options);
            decodeSimpleSubstitutionPreDefinedFile(options[1], options[3], options[5], options[7], options[9], options[11], Constants.CHARACTERS, Constants.DELIMITERS);
            System.out.println("Decode complete!");
        } catch (IOException e) {
            System.err.println("\nI/O error. Exit.");
            e.printStackTrace();
        } catch (CipherHappensException e) {
            System.err.println("\nOops! Something got wrong:");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException | IllegalFormatException e) {
            System.err.println("Invalid options");
            showHelp();
        }
    }

    private static void checkOptionsDefined(final String[] options) throws MissingFormatArgumentException {

        if (options.length != 12 ||
                !options[0].equals(INPUT_FILE_OPTION) ||
                !options[2].equals(INPUT_FILE_ENCODING_OPTION) ||
                !options[4].equals(OUTPUT_FILE_OPTION) ||
                !options[6].equals(OUTPUT_FILE_ENCODING_OPTION) ||
                !options[8].equals(DICTIONARY_OPTION) ||
                !options[10].equals(DICTIONARY_ENCODING_OPTION)) {
            throw new MissingFormatArgumentException("");
        }

    }

    private static void checkOptionsByKey(final String[] options) {

        if (options.length != 12 ||
                !options[0].equals(INPUT_FILE_OPTION) ||
                !options[2].equals(INPUT_FILE_ENCODING_OPTION) ||
                !options[4].equals(OUTPUT_FILE_OPTION) ||
                !options[6].equals(OUTPUT_FILE_ENCODING_OPTION) ||
                !options[8].equals(ALPHABET_OPTION) ||
                !options[10].equals(KEY_OPTION)) {
            throw new MissingFormatArgumentException("");
        }

    }

    private static char[] split(final String s) {

        StringTokenizer st = new StringTokenizer(s);
        char[] res = new char[st.countTokens()];

        for (int i = 0; i < res.length; i++) {

            String token = st.nextToken();

            if (token.length() != 1) {
                throw new MissingFormatArgumentException("");
            }

            res[i] = token.charAt(0);
        }

        return res;
    }

    private static void showHelp() {

        System.out.println();
        System.out.println("Commands and options:");

        System.out.println(DECODE_SIMPLE_SUBSTITUTION_PRE_DEFINED_FILE + " " + INPUT_FILE_OPTION + " \"[input file path]\" " +
                INPUT_FILE_ENCODING_OPTION + " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " +
                OUTPUT_FILE_ENCODING_OPTION + " [output file encoding] " + DICTIONARY_OPTION + " \"[dictionary file path]\" " +
                DICTIONARY_ENCODING_OPTION + " [dictionary encoding]");

        System.out.println(DECODE_CAESAR_PRE_DEFINED_FILE + " " + INPUT_FILE_OPTION + " \"[input file path]\" " +
                INPUT_FILE_ENCODING_OPTION + " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " +
                OUTPUT_FILE_ENCODING_OPTION + " [output file encoding] " + DICTIONARY_OPTION + " \"[dictionary file path]\" " +
                DICTIONARY_ENCODING_OPTION + " [dictionary encoding]");

        System.out.println(DECODE_SIMPLE_SUBSTITUTION_BY_KEY + " " + INPUT_FILE_OPTION + " \"[input file path]\" " +
                INPUT_FILE_ENCODING_OPTION + " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " +
                OUTPUT_FILE_ENCODING_OPTION + " [output file encoding] " + ALPHABET_OPTION + " \"[alphabet character sequence]\" " +
                KEY_OPTION + " \"[encoded character sequence]\"");

        System.out.println(DECODE_CAESAR_BY_KEY + " " + INPUT_FILE_OPTION + " \"[input file path]\" " + INPUT_FILE_ENCODING_OPTION +
                " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " + OUTPUT_FILE_ENCODING_OPTION +
                " [output file encoding] " + ALPHABET_OPTION + " \"[alphabet character sequence]\" " + KEY_OPTION + " [caesar integer key]");

        System.out.println(ENCODE_SIMPLE_SUBSTITUTION + " " + INPUT_FILE_OPTION + " \"[input file path]\" " +
                INPUT_FILE_ENCODING_OPTION + " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " +
                OUTPUT_FILE_ENCODING_OPTION + " [output file encoding] " + ALPHABET_OPTION + " \"[alphabet character sequence]\" " +
                KEY_OPTION + " \"[encoded character sequence]\"");

        System.out.println(ENCODE_CAESAR + " " + INPUT_FILE_OPTION + " \"[input file path]\" " + INPUT_FILE_ENCODING_OPTION +
                " [input file encoding] " + OUTPUT_FILE_OPTION + " \"[output file path]\" " + OUTPUT_FILE_ENCODING_OPTION +
                " [output file encoding] " + ALPHABET_OPTION + " \"[alphabet character sequence]\" " + KEY_OPTION + " [caesar integer key]");

    }

    public static void decodeSimpleSubstitutionPreDefinedFile(final String inputFileName, final String inputFileEncoding,
                                                              final String outputFileName, final String outputFileEncoding,
                                                              final String dictionaryFileName, final String dictionaryFileEncoding,
                                                              final char[] alphabet, final char[] delimiters) throws IOException, CipherHappensException {

        Dictionary dictionary = new Dictionary(dictionaryFileName, dictionaryFileEncoding);
        char[] statistic = FrequencyAnalyzer.getSortedFrequencyStatisticFromFile(inputFileName, inputFileEncoding, alphabet);

        ArrayList<char[]> permutations = Permutator.generateNthPermutations(statistic, 4);
        SubstitutionKey bestDecodeKey = null;
        int bestDecodeKeyQuality = 0;

        try {

            for (char[] permutation : permutations) {

                SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, permutation);

//                substitutionKey.definePair('а', ' ');
                substitutionKey.definePair('б', 'к');
                substitutionKey.definePair('в', 'з');
                substitutionKey.definePair('г', 'р');
                substitutionKey.definePair('д', 'н');
//                substitutionKey.definePair('е', ' ');
                substitutionKey.definePair('ж', 'а');
//                substitutionKey.definePair('з', ' ');
//                substitutionKey.definePair('и', ' ');
                //substitutionKey.definePair('й', 'ф');
                substitutionKey.definePair('к', 'у');
//                substitutionKey.definePair('л', ' ');
//                substitutionKey.definePair('м', ' ');
                substitutionKey.definePair('н', 'е');
//                substitutionKey.definePair('о', ' ');
                substitutionKey.definePair('п', 'ж');
                substitutionKey.definePair('р', 'л');
                substitutionKey.definePair('с', 'м');
                substitutionKey.definePair('т', 'э');
                substitutionKey.definePair('у', 'п');
                substitutionKey.definePair('ф', 'ч');
                substitutionKey.definePair('х', 'х');
                substitutionKey.definePair('ц', 'ю');
                substitutionKey.definePair('ч', 'щ');
                substitutionKey.definePair('ш', 'ц');
                substitutionKey.definePair('щ', 'ь');
                substitutionKey.definePair('ъ', 'ы');
                substitutionKey.definePair('ы', 'о');
                substitutionKey.definePair('ь', 'ш');
//                substitutionKey.definePair('э', 'ъ');
//                substitutionKey.definePair('ю', 'й');
                substitutionKey.definePair('я', 'д');

                int quality = FileDecoder.getDecodeQualityByKey(inputFileName, inputFileEncoding, substitutionKey, dictionary, delimiters);

                if (quality > bestDecodeKeyQuality) {
                    bestDecodeKeyQuality = quality;
                    bestDecodeKey = substitutionKey;
                }
            }

            if (bestDecodeKey != null) {

                System.out.println("Fount best decode: " + bestDecodeKeyQuality + " words from dictionary matches");
                System.out.println("\nOriginal alphabet:");

                for (char c : bestDecodeKey.getOriginalAlphabet()) {
                    System.out.print(c + " ");
                }

                System.out.println("\n\nEncoded alphabet:");

                for (char c : bestDecodeKey.getEncodedAlphabet()) {
                    System.out.print(c + " ");
                }

                FileDecoder.decodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, bestDecodeKey);

                System.out.println("\n\nDecoded file created");

            } else {
                System.out.println("Best decode quality is 0. Try to define another substitution");
            }

        } catch (CipherHappensException e) {
            System.err.println("Oops! Something got wrong");
            e.printStackTrace();
        }
    }

    public static void encodeCaesarByKey(final String inputFileName, final String inputFileEncoding,
                                         final String outputFileName, final String outputFileEncoding,
                                         final char[] alphabet, final int key) throws IOException, CipherHappensException {



        SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, key);
        FileEncoder.encodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, substitutionKey);
    }

    public static void decodeCaesarByKey(final String inputFileName, final String inputFileEncoding,
                                         final String outputFileName, final String outputFileEncoding,
                                         final char[] alphabet, final int key) throws IOException, CipherHappensException {

        SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, key);
        FileDecoder.decodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, substitutionKey);
    }

    public static void encodeSimpleSubstitutionByKey(final String inputFileName, final String inputFileEncoding,
                                                     final String outputFileName, final String outputFileEncoding,
                                                     final char[] alphabet, final char[] key) throws IOException, CipherHappensException {

        SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, key);
        FileEncoder.encodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, substitutionKey);
    }

    public static void decodeSimpleSubstitutionByKey(final String inputFileName, final String inputFileEncoding,
                                         final String outputFileName, final String outputFileEncoding,
                                         final char[] alphabet, final char[] key) throws IOException, CipherHappensException {

        SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, key);
        FileDecoder.decodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, substitutionKey);
    }

    public static void decodeCaesarPreDefinedFile(final String inputFileName, final String inputFileEncoding,
                                                  final String outputFileName, final String outputFileEncoding,
                                                  final String dictionaryFileName, final String dictionaryFileEncoding,
                                                  final char[] alphabet, final char[] delimiters) throws IOException, CipherHappensException {

        Dictionary dictionary = new Dictionary(dictionaryFileName, dictionaryFileEncoding);
        char[] statistic = FrequencyAnalyzer.getSortedFrequencyStatisticFromFile(inputFileName, inputFileEncoding, alphabet);

        ArrayList<char[]> permutations = Permutator.generateCaesarPermutations(statistic, alphabet, 4);
        SubstitutionKey bestDecodeKey = null;
        int bestDecodeKeyQuality = 0;

        try {

            for (char[] permutation : permutations) {

                SubstitutionKey substitutionKey = new SubstitutionKey(alphabet, permutation);

                int quality = FileDecoder.getDecodeQualityByKey(inputFileName, inputFileEncoding, substitutionKey, dictionary, delimiters);

                if (quality > bestDecodeKeyQuality) {
                    bestDecodeKeyQuality = quality;
                    bestDecodeKey = substitutionKey;
                }
            }

            if (bestDecodeKey != null) {

                System.out.println("Fount best decode: " + bestDecodeKeyQuality + " words from dictionary matches");
                System.out.println("Original alphabet:\n");

                for (char c : bestDecodeKey.getOriginalAlphabet()) {
                    System.out.print(c + " ");
                }

                System.out.println("\n\nEncoded alphabet:");

                for (char c : bestDecodeKey.getEncodedAlphabet()) {
                    System.out.print(c + " ");
                }

                System.out.println("\n\nCaesar key:");
                System.out.println(getCaesarKey(bestDecodeKey.getOriginalAlphabet(), bestDecodeKey.getEncodedAlphabet()));

                FileDecoder.decodeFileByKey(inputFileName, inputFileEncoding, outputFileName, outputFileEncoding, bestDecodeKey);

                System.out.println("\n\nDecoded file created");

            } else {
                System.out.println("Best decode quality is 0. Try to define another substitution");
            }

        } catch (CipherHappensException e) {
            System.err.println("Oops! Something got wrong");
            e.printStackTrace();
        }
    }

    private static int getCaesarKey(final char[] originalAlphabet, final char[] encodedAlphabet) {

        char original = originalAlphabet[0];
        char encoded = encodedAlphabet[0];

        return Math.min(encoded - original, Math.abs(encoded - originalAlphabet.length) + original);
    }
}

