package main;

import com.google.common.base.Preconditions;
import utils.Constants;

/**
 * @author vladislav.trofimov@emc.com
 */
public class BlockPermutator {

    public static boolean[] getInitialPermutation(final boolean[] dataBlock) {

        Preconditions.checkNotNull(dataBlock, "Can not perform permutation without data block");
        Preconditions.checkArgument(dataBlock.length == Constants.BLOCK_BIT_LENGTH, "Data block must contains %s bits", Constants.BLOCK_BIT_LENGTH);

        boolean[] converted = new boolean[Constants.BLOCK_BIT_LENGTH];

        for (int i = 0; i < Constants.BLOCK_BIT_LENGTH; i++) {
            converted[i] = dataBlock[Constants.P[i] - 1];
        }

        return converted;
    }

    public static boolean[] getFinalPermutation(final boolean[] dataBlock) {

        Preconditions.checkNotNull(dataBlock, "Can not perform permutation without data block");
        Preconditions.checkArgument(dataBlock.length == Constants.BLOCK_BIT_LENGTH, "Data block must contains %s bits", Constants.BLOCK_BIT_LENGTH);

        boolean[] converted = new boolean[Constants.BLOCK_BIT_LENGTH];

        for (int i = 0; i < Constants.BLOCK_BIT_LENGTH; i++) {
            converted[i] = dataBlock[Constants.P_1[i] - 1];
        }

        return converted;
    }

}