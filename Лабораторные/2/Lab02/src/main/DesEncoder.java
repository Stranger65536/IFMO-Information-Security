package main;

import com.google.common.base.Preconditions;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static utils.Constants.*;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesEncoder {

    /**
     * Extracting left part of specified 64 bits block bits
     *
     * @param block specified 64 bits block
     * @return binary representation of extracted part
     */
    public static boolean[] getLeftPartOfBlock(final boolean[] block) {

        Preconditions.checkNotNull(block, "Can not extract block part without data");
        Preconditions.checkArgument(block.length == BLOCK_BIT_LENGTH, "Block must contains %s bits", BLOCK_BIT_LENGTH);

        return Arrays.copyOfRange(block, 0, BLOCK_PART_BIT_LENGTH);
    }

    /**
     * Extracting right part of specified 64 bits block bits
     *
     * @param block specified 64 bits block
     * @return binary representation of extracted part
     */
    public static boolean[] getRightPartOfBlock(final boolean[] block) {

        Preconditions.checkNotNull(block, "Can not extract block part without data");
        Preconditions.checkArgument(block.length == BLOCK_BIT_LENGTH, "Block must contains %s bits", BLOCK_BIT_LENGTH);

        return Arrays.copyOfRange(block, BLOCK_PART_BIT_LENGTH, BLOCK_BIT_LENGTH);
    }

    /**
     * Xoring two specified 32 bits blocks
     *
     * @param leftBlock  specified 32 bits block
     * @param rightBlock specified 32 bits block
     * @return binary representation of xored block
     */
    public static boolean[] xorBlocks(final boolean[] leftBlock, final boolean[] rightBlock) {

        Preconditions.checkNotNull(leftBlock, "Can not xor block part without data");
        Preconditions.checkArgument(leftBlock.length == BLOCK_PART_BIT_LENGTH, "Block part must contains %s bits", BLOCK_PART_BIT_LENGTH);
        Preconditions.checkNotNull(rightBlock, "Can not xor block part without data");
        Preconditions.checkArgument(rightBlock.length == BLOCK_PART_BIT_LENGTH, "Block part must contains %s bits", BLOCK_PART_BIT_LENGTH);

        boolean[] converted = new boolean[BLOCK_PART_BIT_LENGTH];

        for (int i = 0; i < BLOCK_PART_BIT_LENGTH; i++) {
            converted[i] = leftBlock[i] ^ rightBlock[i];
        }

        return converted;
    }

    /**
     * Merges two specified 32 bits blocks into one 64 bits block
     *
     * @param leftBlock  specified 32 bits block
     * @param rightBlock specified 32 bits block
     * @return binary representation of merged block
     */
    public static boolean[] mergeBlocks(boolean[] leftBlock, boolean[] rightBlock) {

        Preconditions.checkNotNull(leftBlock, "Can not xor block part without data");
        Preconditions.checkArgument(leftBlock.length == BLOCK_PART_BIT_LENGTH, "Block part must contains %s bits", BLOCK_PART_BIT_LENGTH);
        Preconditions.checkNotNull(rightBlock, "Can not xor block part without data");
        Preconditions.checkArgument(rightBlock.length == BLOCK_PART_BIT_LENGTH, "Block part must contains %s bits", BLOCK_PART_BIT_LENGTH);

        boolean[] converted = new boolean[BLOCK_BIT_LENGTH];

        System.arraycopy(leftBlock, 0, converted, 0, BLOCK_PART_BIT_LENGTH);
        System.arraycopy(rightBlock, 0, converted, BLOCK_PART_BIT_LENGTH, BLOCK_PART_BIT_LENGTH);

        return converted;
    }

    /**
     * Encodes specified 64 bits block with specified array of additional keys
     *
     * @param block specified 64 bits block
     * @param keys  specified array of additional 48 bits keys
     * @return binary representation of encoded block
     */
    public static byte[] encodeBlock(byte[] block, ArrayList<boolean[]> keys) {

        Preconditions.checkNotNull(block, "Can not encode block without data");
        Preconditions.checkArgument(block.length == BLOCK_BYTE_LENGTH, "Block must contains %s bits", BLOCK_BYTE_LENGTH);
        Preconditions.checkNotNull(keys, "Can not encode block without keys");
        Preconditions.checkArgument(keys.size() == ROUNDS, "Block must be encoded with %s additional keys", ROUNDS);

        boolean[] binaryBlock = Utils.asBinaryArray(block);
        boolean[] initiallyPermutedBlock = BlockPermutator.getInitialPermutation(binaryBlock);

        boolean[] leftPart = getLeftPartOfBlock(initiallyPermutedBlock);
        boolean[] rightPart = getRightPartOfBlock(initiallyPermutedBlock);

        for (int i = 0; i < ROUNDS - 1; i++) {

            boolean[] nextRight = xorBlocks(leftPart, new DesEncodingFunction(rightPart, keys.get(i)).getEncodedValue());
            boolean[] nextLeft = rightPart;

            rightPart = nextRight;
            leftPart = nextLeft;

        }

        leftPart = xorBlocks(leftPart, new DesEncodingFunction(rightPart, keys.get(ROUNDS - 1)).getEncodedValue());
        boolean[] merged = mergeBlocks(leftPart, rightPart);
        boolean[] encoded = BlockPermutator.getFinalPermutation(merged);

        return Utils.asByteArray(encoded);
    }

    /**
     * Encodes specified 64 bits block with specified user key
     *
     * @param block  specified 64 bits block
     * @param desKey specified user key
     * @return binary representation of encoded block
     */
    public static byte[] encode(byte[] block, DesKey desKey) {

        Preconditions.checkNotNull(block, "Can not encode block without data");
        Preconditions.checkArgument(block.length == BLOCK_BYTE_LENGTH, "Block must contains %s bytes", BLOCK_BYTE_LENGTH);
        Preconditions.checkNotNull(desKey, "Can not encode block without key");

        return encodeBlock(block, desKey.getEncodeKeys());
    }

    /**
     * Decodes specified 64 bits block with specified user key
     *
     * @param block  specified 64 bits block
     * @param desKey specified user key
     * @return binary representation of decoded block
     */
    public static byte[] decode(byte[] block, DesKey desKey) {

        Preconditions.checkNotNull(block, "Can not encode block without data");
        Preconditions.checkArgument(block.length == BLOCK_BYTE_LENGTH, "Block must contains %s bytes", BLOCK_BYTE_LENGTH);
        Preconditions.checkNotNull(desKey, "Can not encode block without key");

        return encodeBlock(block, desKey.getDecodeKeys());
    }

}
