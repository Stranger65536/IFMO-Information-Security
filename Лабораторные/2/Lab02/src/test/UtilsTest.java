package test;

import com.google.common.base.Preconditions;
import org.junit.Test;
import utils.Utils;

import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
public class UtilsTest {

    @Test
    public void getBinaryBlockByteArrayTest() {
        byte[] in = new byte[]{1, 2, 3, 4, 5, 6, 7, -120};
        boolean[] out = Utils.asBinaryArray(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 1,
                0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 1, 1,
                0, 0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 1, 1, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getBinaryBlockLongTest() {
        long in = 72623859790382984l;
        boolean[] out = Utils.asByteArray(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 1,
                0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 1, 1,
                0, 0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 1, 1, 0,
                0, 0, 0, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getByteBlockTest() {
        boolean[] in = new boolean[]{
                false, false, false, false, false, false, false, true,
                false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, true, true,
                false, false, false, false, false, true, false, false,
                false, false, false, false, false, true, false, true,
                false, false, false, false, false, true, true, false,
                false, false, false, false, false, true, true, true,
                true, false, false, false, true, false, false, false
        };
        byte[] out = Utils.asByteArray(in);
        byte[] outTest = new byte[]{1, 2, 3, 4, 5, 6, 7, -120};
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void convertTest() {
        byte[] in = new byte[]{1, 0, 1, 0, 1, 1, 0, 0};
        boolean[] out = Utils.translateToBinaryArray(in);
        boolean[] outTest = new boolean[]{true, false, true, false, true, true, false, false};
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void compareTestBooleanSuccess() {
        boolean[] left = new boolean[]{false, false, true, true, false};
        boolean[] right = new boolean[]{false, false, true, true, false};
        Preconditions.checkArgument(Utils.checkEquals(left, right));
    }

    @Test(expected = IllegalArgumentException.class)
    public void compareTestBooleanFail() {
        boolean[] left = new boolean[]{false, false, true, true, false};
        boolean[] right = new boolean[]{false, false, false, true, false};
        Preconditions.checkArgument(Utils.checkEquals(left, right));
    }

    @Test
    public void compareTestByteSuccess() {
        byte[] left = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] right = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        Preconditions.checkArgument(Utils.checkEquals(left, right));
    }

    @Test(expected = IllegalArgumentException.class)
    public void compareTestByteFail() {
        byte[] left = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] right = new byte[]{1, 2, 3, 4, 5, 2, 7, 8};
        Preconditions.checkArgument(Utils.checkEquals(left, right));
    }
}