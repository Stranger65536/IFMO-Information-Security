package test;

import com.google.common.base.Preconditions;
import main.DesEncodingFunction;
import org.junit.Test;
import utils.Constants;
import utils.Utils;

import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesEncodingFunctionTest {

    @Test
    public void getExpandedBlockTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        boolean[] out = DesEncodingFunction.getExpandedBlock(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1,
                0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getXoredWithKeyBlockTest() {
        boolean[] inBlock = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1,
                0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0
        });
        boolean[] inKey = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0,
                0, 0, 0, 0, 1, 0,
                1, 1, 0, 0, 1, 0,
                1, 0, 1, 1, 0, 0,
                0, 1, 0, 1, 0, 1,
                1, 1, 0, 0, 1, 0,
                1, 0, 1, 0, 1, 1,
                0, 0, 0, 0, 1, 0
        });
        boolean[] out = DesEncodingFunction.getXoredWithKeyBlock(inBlock, inKey);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0,
                0, 0, 0, 0, 1, 1,
                1, 0, 1, 1, 0, 1,
                0, 1, 0, 0, 1, 1,
                1, 0, 0, 1, 0, 1,
                1, 1, 0, 0, 1, 0,
                1, 0, 0, 1, 1, 1,
                0, 0, 1, 1, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest1() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{0, 1, 0, 1, 0, 0});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S1);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{0, 1, 1, 0});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest2() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{0, 0, 0, 0, 1, 1});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S2);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{1, 1, 0, 1});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest3() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{1, 0, 1, 1, 0, 1});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S3);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{1, 0, 0, 0});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest4() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{0, 1, 0, 0, 1, 1});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S4);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{0, 1, 1, 1});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest5() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{1, 0, 0, 1, 0, 1});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S5);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{1, 1, 0, 0});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest6() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{1, 1, 0, 0, 1, 0});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S6);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{0, 0, 0, 0});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest7() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{1, 0, 0, 1, 1, 1});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S7);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{1, 0, 0, 0});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getTransformedBlockTest8() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{0, 0, 1, 1, 1, 0});
        boolean[] out = DesEncodingFunction.getTransformedBlock(in, Constants.S8);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{0, 0, 0, 1});
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getFinallyPermutedBlockTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 1, 1, 0,
                1, 1, 0, 1,
                1, 0, 0, 0,
                0, 1, 1, 1,
                1, 1, 0, 0,
                0, 0, 0, 0,
                1, 0, 0, 0,
                0, 0, 0, 1
        });
        boolean[] out = DesEncodingFunction.getFinallyPermutedBlock(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 0,
                0, 0, 0, 1,
                0, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 0, 1,
                1, 0, 1, 1,
                0, 0, 0, 1,
                0, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void desEncodingFunctionTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        boolean[] key = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0, 0, 0,
                0, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 1, 1, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1,
                0, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 0, 0, 0, 0, 1, 0
        });
        boolean[] out = new DesEncodingFunction(in, key).getEncodedValue();
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 0, 0, 0, 0, 1,
                0, 1, 0, 0, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 0, 1, 1,
                0, 0, 0, 1, 0, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }
}
