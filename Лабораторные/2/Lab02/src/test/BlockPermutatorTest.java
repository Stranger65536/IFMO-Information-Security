package test;

import com.google.common.base.Preconditions;
import main.BlockPermutator;
import org.junit.Test;
import utils.Utils;

import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
public class BlockPermutatorTest {

    @Test
    public void getInitialPermutationTest() {
        boolean[] in = Utils.asBinaryArray(new byte[]{
                49, 50, 51, 52, 53, 54, 55, 56
        });
        boolean[] out = BlockPermutator.getInitialPermutation(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getFinalPermutationTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 1, 1, 0, 0, 1, 0,
                0, 0, 1, 1, 0, 0, 1, 1,
                0, 1, 1, 0, 0, 0, 0, 1,
                1, 0, 1, 0, 0, 0, 0, 0,
                1, 1, 1, 0, 1, 0, 1, 1,
                0, 0, 0, 1, 0, 0, 0, 0,
                1, 1, 0, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 1

        });
        boolean[] out = BlockPermutator.getFinalPermutation(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 1, 0, 1, 1, 0,
                1, 1, 0, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0,
                1, 0, 0, 0, 1, 0, 0, 0,
                0, 1, 1, 1, 1, 0, 0, 0,
                1, 1, 0, 1, 0, 1, 0, 1,
                1, 0, 0, 0, 1, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

}