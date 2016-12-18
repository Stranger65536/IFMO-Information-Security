package utils;

import com.google.common.base.Preconditions;

import java.math.BigInteger;

import static utils.Constants.*;

/**
 * @author vladislav.trofimov@emc.com
 */
public class Utils {

    public static boolean[] asBinaryArray(final byte[] block) {

        Preconditions.checkNotNull(block, "Can not convert block without data");
        Preconditions.checkArgument(block.length == BLOCK_BYTE_LENGTH, "Data block must contains %s bytes", BLOCK_BYTE_LENGTH);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < BLOCK_BYTE_LENGTH; i++) {
            stringBuilder.append(String.format("%8s", Integer.toBinaryString(block[i] & 0xFF)).replace(' ', '0'));
        }

        String stringResult = stringBuilder.toString();
        boolean[] converted = new boolean[BLOCK_BIT_LENGTH];

        for (int i = stringResult.length() - 1, j = BLOCK_BIT_LENGTH; i >= 0; i--) {
            converted[--j] = stringResult.charAt(i) == '1';
        }

        return converted;
    }

    public static boolean[] asByteArray(long block) {

        Preconditions.checkNotNull(block, "Can not convert block without data");

        boolean[] convertedData = new boolean[KEY_BIT_LENGTH];
        String stringResult = Long.toBinaryString(block);

        for (int i = stringResult.length() - 1, j = KEY_BIT_LENGTH; i >= 0; i--) {
            convertedData[--j] = stringResult.charAt(i) == '1';
        }

        return convertedData;
    }

    public static byte[] asByteArray(final boolean[] block) {

        Preconditions.checkNotNull(block, "Can not convert block without data");
        Preconditions.checkArgument(block.length == BLOCK_BIT_LENGTH, "Data block must contains %s bits", BLOCK_BIT_LENGTH);

        byte[] result = new byte[BLOCK_BYTE_LENGTH];

        for (int i = 0; i < BLOCK_BYTE_LENGTH; i++) {

            String s = "";

            for (int j = i * 8; j < i * 8 + 8; j++) {
                s += (block[j]) ? "1" : "0";
            }

            result[i] = (byte) Integer.parseInt(s, 2);
        }

        return result;
    }

    public static boolean[] translateToBinaryArray(final byte[] block) {

        Preconditions.checkNotNull(block, "Can not convert block without data");

        boolean[] result = new boolean[block.length];

        for (int i = 0; i < block.length; i++) {
            result[i] = (block[i] == 1);
        }

        return result;
    }

    public static boolean checkEquals(final boolean[] left, final boolean[] right) {

        Preconditions.checkNotNull(left);
        Preconditions.checkNotNull(right);
        Preconditions.checkState(left.length == right.length);

        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                throw new IllegalArgumentException("Difference at " + i + " (+1 in vector)");
            }
        }

        return true;
    }

    public static boolean checkEquals(final byte[] left, final byte[] right) {

        Preconditions.checkNotNull(left);
        Preconditions.checkNotNull(right);
        Preconditions.checkState(left.length == right.length);

        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                throw new IllegalArgumentException("Difference at " + i);
            }
        }

        return true;
    }

}