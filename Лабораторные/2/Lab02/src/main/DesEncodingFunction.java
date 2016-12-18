package main;

import com.google.common.base.Preconditions;
import utils.Constants;

import java.util.Arrays;

import static utils.Constants.*;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesEncodingFunction {

    /**
     * Binary representation of encoded block
     */
    private final boolean[] encodedValue;

    /**
     * Encoding specified 32 bits block using specified 48 bits additional key
     *
     * @param block specified 32 bits block
     * @param key   specified 48 bits additional key
     */
    public DesEncodingFunction(final boolean[] block, final boolean[] key) {

        Preconditions.checkNotNull(block, "Can not encode block without data");
        Preconditions.checkArgument(block.length == BLOCK_PART_BIT_LENGTH, "Block must contains %s bits", BLOCK_PART_BIT_LENGTH);
        Preconditions.checkNotNull(key, "Can not encode block without key");
        Preconditions.checkArgument(key.length == ADDITIONAL_KEY_BIT_LENGTH, "Key must contains %s bits", ADDITIONAL_KEY_BIT_LENGTH);

        boolean[] preparedBlock = getXoredWithKeyBlock(getExpandedBlock(block), key);

        boolean[] transformedSubBlock1 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 0, TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S1);
        boolean[] transformedSubBlock2 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 2 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S2);
        boolean[] transformedSubBlock3 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 2 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 3 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S3);
        boolean[] transformedSubBlock4 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 3 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 4 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S4);
        boolean[] transformedSubBlock5 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 4 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 5 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S5);
        boolean[] transformedSubBlock6 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 5 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 6 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S6);
        boolean[] transformedSubBlock7 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 6 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 7 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S7);
        boolean[] transformedSubBlock8 = getTransformedBlock(Arrays.copyOfRange(preparedBlock, 7 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, 8 * TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH), S8);

        boolean[] mergedBlock = new boolean[BLOCK_PART_BIT_LENGTH];

        System.arraycopy(transformedSubBlock1, 0, mergedBlock, 0, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock2, 0, mergedBlock, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock3, 0, mergedBlock, 2 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock4, 0, mergedBlock, 3 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock5, 0, mergedBlock, 4 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock6, 0, mergedBlock, 5 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock7, 0, mergedBlock, 6 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);
        System.arraycopy(transformedSubBlock8, 0, mergedBlock, 7 * TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);

        encodedValue = getFinallyPermutedBlock(mergedBlock);
    }

    /**
     * Expands specified 32 bits block to 48 bits block using E permutation
     *
     * @param block specified 32 bits block
     * @return binary representation of expanded block
     */
    public static boolean[] getExpandedBlock(final boolean[] block) {

        Preconditions.checkNotNull(block, "Can not expand block without data");
        Preconditions.checkArgument(block.length == BLOCK_PART_BIT_LENGTH, "Block must contains %s bits", BLOCK_PART_BIT_LENGTH);

        boolean[] converted = new boolean[EXPANDED_BLOCK_BIT_LENGTH];

        for (int i = 0; i < EXPANDED_BLOCK_BIT_LENGTH; i++) {
            boolean bit = block[Constants.E[i] - 1];
            converted[i] = bit;
        }

        return converted;
    }

    /**
     * Xoring specified 48 bits block with specified 48 bits additional key
     *
     * @param block specified 48 bits block
     * @param key   specified 48 bits additional key
     * @return binary representation of xored result
     */
    public static boolean[] getXoredWithKeyBlock(final boolean[] block, final boolean[] key) {

        Preconditions.checkNotNull(block, "Can not xor block without data");
        Preconditions.checkArgument(block.length == EXPANDED_BLOCK_BIT_LENGTH, "Block must contains %s bits", EXPANDED_BLOCK_BIT_LENGTH);
        Preconditions.checkNotNull(key, "Can not xor key without data");
        Preconditions.checkArgument(key.length == ADDITIONAL_KEY_BIT_LENGTH, "Key must contains %s bits", ADDITIONAL_KEY_BIT_LENGTH);

        boolean[] converted = new boolean[EXPANDED_BLOCK_BIT_LENGTH];

        for (int i = 0; i < EXPANDED_BLOCK_BIT_LENGTH; i++) {
            converted[i] = block[i] ^ key[i];
        }

        return converted;
    }

    /**
     * Transforms specified 6 bits block to 4 bits block using specified 4 * 6 matrix
     *
     * @param block        specified 6 bits block
     * @param shrinkMatrix specified 4 * 6 matrix
     * @return binary representation of transformed block
     */
    public static boolean[] getTransformedBlock(final boolean[] block, final byte[][] shrinkMatrix) {

        Preconditions.checkNotNull(block, "Can not transform block without data");
        Preconditions.checkArgument(block.length == TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH, "Block must contains %s bits", TRANSFORMATION_BLOCK_INPUT_BIT_LENGTH);
        Preconditions.checkNotNull(shrinkMatrix, "Can not transform block without matrix");
        Preconditions.checkArgument(shrinkMatrix.length == TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH, "Matrix must contains %s rows", TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH);

        int row = Integer.parseInt((block[0] ? "1" : "0") + (block[5] ? "1" : "0"), 2);
        int column = Integer.parseInt((block[1] ? "1" : "0") + (block[2] ? "1" : "0") + (block[3] ? "1" : "0") + (block[4] ? "1" : "0"), 2);

        String result = Integer.toBinaryString(shrinkMatrix[row][column]);

        boolean[] converted = new boolean[TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH];

        for (int i = result.length() - 1, j = TRANSFORMATION_BLOCK_OUTPUT_BIT_LENGTH; i >= 0; i--) {
            converted[--j] = result.charAt(i) == '1';
        }

        return converted;
    }

    /**
     * Permutes specified 32 bits block with P2 permutation
     *
     * @param block specified 32 bits block
     * @return binary representation of permuted block
     */
    public static boolean[] getFinallyPermutedBlock(final boolean[] block) {

        Preconditions.checkNotNull(block, "Can not permute block without data");
        Preconditions.checkArgument(block.length == BLOCK_PART_BIT_LENGTH, "Block must contains %s bits", BLOCK_PART_BIT_LENGTH);

        boolean[] converted = new boolean[BLOCK_PART_BIT_LENGTH];

        for (int i = 0; i < BLOCK_PART_BIT_LENGTH; i++) {
            boolean bit = block[P2[i] - 1];
            converted[i] = bit;
        }

        return converted;
    }

    public boolean[] getEncodedValue() {
        return encodedValue;
    }

}