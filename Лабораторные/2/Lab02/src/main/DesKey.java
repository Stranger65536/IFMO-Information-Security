package main;

import com.google.common.base.Preconditions;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static utils.Constants.*;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesKey {

    /**
     * Additional keys to use in each round of encoding
     */
    private final ArrayList<boolean[]> encodeKeys;

    /**
     * Additional keys to use in each round of decoding
     */
    private final ArrayList<boolean[]> decodeKeys;

    /**
     * Creating an DES 64 bits key using specified seed value.
     * Generating additional keys for all rounds.
     *
     * @param data specified seed for key
     */
    public DesKey(final long data) {

        encodeKeys = new ArrayList<>(ROUNDS);

        boolean[] initialPermutated = getInitiallyPermutedShrinkedKey(Utils.asByteArray(data));

        boolean[] leftPart = getLeftPartOfInitiallyPermutatedKey(initialPermutated);
        boolean[] rightPart = getRightPartOfInitiallyPermutatedKey(initialPermutated);

        for (int i = 0; i < ROUNDS; i++) {

            boolean[] leftShiftedPart = getLeftCircularShiftOfPartitionedKey(leftPart, Constants.S[i]);
            boolean[] rightShiftedPart = getLeftCircularShiftOfPartitionedKey(rightPart, Constants.S[i]);

            boolean[] additionalKey = getFinallyPermutedKey(getUnifiedKey(leftShiftedPart, rightShiftedPart));

            leftPart = leftShiftedPart;
            rightPart = rightShiftedPart;

            encodeKeys.add(additionalKey);
        }

        Collections.reverse(decodeKeys = new ArrayList<>(encodeKeys));
    }

    /**
     * Shrink and permutes specified 64 bits key to 56 bits key
     *
     * @param key specified 64 bits key
     * @return binary representation of shrinked and permuted key
     */
    public static boolean[] getInitiallyPermutedShrinkedKey(final boolean[] key) {

        Preconditions.checkNotNull(key, "Can not permute key without data");
        Preconditions.checkArgument(key.length == KEY_BIT_LENGTH, "User key must contains %s bits", KEY_BIT_LENGTH);

        boolean[] converted = new boolean[KEY_BIT_LENGTH_AFTER_G];

        for (int i = 0; i < KEY_BIT_LENGTH_AFTER_G; i++) {
            boolean bit = key[G[i] - 1];
            converted[i] = bit;
        }

        return converted;
    }

    /**
     * Extract left part of 28 bits from specified 56 bits key
     *
     * @param key specified 56 bits key
     * @return binary representation of left part of 28 bits from specified 56 bits key
     */
    public static boolean[] getLeftPartOfInitiallyPermutatedKey(final boolean[] key) {

        Preconditions.checkNotNull(key, "Can not partition key without data");
        Preconditions.checkArgument(key.length == KEY_BIT_LENGTH_AFTER_G, "Shrinked user key must contains %s bits", KEY_BIT_LENGTH_AFTER_G);

        return Arrays.copyOfRange(key, 0, KEY_PART_BIT_LENGTH);
    }

    /**
     * Extract right part of 28 bits from specified 56 bits key
     *
     * @param key specified 56 bits key
     * @return binary representation of right part of 28 bits from specified 56 bits key
     */
    public static boolean[] getRightPartOfInitiallyPermutatedKey(final boolean[] key) {

        Preconditions.checkNotNull(key, "Can not partition key without data");
        Preconditions.checkArgument(key.length == KEY_BIT_LENGTH_AFTER_G, "Shrinked user key must contains %s bits", KEY_BIT_LENGTH_AFTER_G);

        return Arrays.copyOfRange(key, KEY_PART_BIT_LENGTH, KEY_BIT_LENGTH_AFTER_G);
    }

    /**
     * Performs the left-shifting specified key part with specified shifting count
     *
     * @param key        specified 28 bits key part
     * @param shiftCount specified shifts count
     * @return binary representation of shifted key
     */
    public static boolean[] getLeftCircularShiftOfPartitionedKey(final boolean[] key, final int shiftCount) {

        Preconditions.checkNotNull(key, "Can not perform key shifting without data");
        Preconditions.checkArgument(key.length == KEY_PART_BIT_LENGTH, "Key part must contains %s bits", KEY_PART_BIT_LENGTH);
        Preconditions.checkArgument(shiftCount >= 0, "Key must be shifted with positive count of shifts");

        boolean[] permutation = new boolean[key.length];

        for (int j = 0; j < key.length; j++) {
            permutation[j] = key[(shiftCount + j) % key.length];
        }

        return permutation;
    }

    /**
     * Merges two specified 28 bits parts of key into unified 56 bits key
     *
     * @param leftPart  specified left part of key
     * @param rightPart specified right part of key
     * @return binary representation of merged key
     */
    public static boolean[] getUnifiedKey(final boolean[] leftPart, final boolean[] rightPart) {

        Preconditions.checkNotNull(leftPart, "Can not permute key without data");
        Preconditions.checkArgument(leftPart.length == KEY_PART_BIT_LENGTH, "Key part must contains %s bits", KEY_PART_BIT_LENGTH);
        Preconditions.checkNotNull(rightPart, "Can not permute key without data");
        Preconditions.checkArgument(rightPart.length == KEY_PART_BIT_LENGTH, "Key part must contains %s bits", KEY_PART_BIT_LENGTH);

        boolean[] merged = new boolean[KEY_BIT_LENGTH_AFTER_G];

        System.arraycopy(leftPart, 0, merged, 0, KEY_PART_BIT_LENGTH);
        System.arraycopy(rightPart, 0, merged, KEY_PART_BIT_LENGTH, KEY_PART_BIT_LENGTH);

        return merged;
    }

    /**
     * Permutes specified 56 bits key using KP vector
     *
     * @param key specified 56 bits key
     * @return binary representation of permuted key
     */
    public static boolean[] getFinallyPermutedKey(final boolean[] key) {

        Preconditions.checkNotNull(key, "Can not permute key without data");
        Preconditions.checkArgument(key.length == KEY_BIT_LENGTH_AFTER_G, "Key must contains %s bits", KEY_BIT_LENGTH_AFTER_G);

        boolean[] converted = new boolean[ADDITIONAL_KEY_BIT_LENGTH];

        for (int i = 0; i < ADDITIONAL_KEY_BIT_LENGTH; i++) {
            boolean bit = key[KP[i] - 1];
            converted[i] = bit;
        }

        return converted;
    }

    public ArrayList<boolean[]> getEncodeKeys() {
        return encodeKeys;
    }

    public ArrayList<boolean[]> getDecodeKeys() {
        return decodeKeys;
    }

}