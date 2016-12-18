package test;

import com.google.common.base.Preconditions;
import main.DesKey;
import org.junit.Test;
import utils.Utils;

import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesKeyTest {

    @Test
    public void getInitiallyPermutedShrinkedKeyTest() {
        boolean[] key = Utils.asByteArray(3544952156018063160l);
        boolean[] out = DesKey.getInitiallyPermutedShrinkedKey(key);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 1, 1, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getLeftPartOfInitiallyPermutatedKeyTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 1, 1, 1, 1
        });
        boolean[] out = DesKey.getLeftPartOfInitiallyPermutatedKey(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getRightPartOfInitiallyPermutatedKeyTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 1, 1, 1, 1
        });
        boolean[] out = DesKey.getRightPartOfInitiallyPermutatedKey(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 1, 1, 0, 0, 1, 1,
                0, 0, 1, 1, 1, 1, 0,
                0, 0, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getLeftCircularShiftOfPartitionedKeyTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 1, 1, 0, 0, 1, 1,
                0, 0, 1, 1, 1, 1, 0,
                0, 0, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1
        });
        boolean[] out = DesKey.getLeftCircularShiftOfPartitionedKey(in, 1);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                1, 1, 0, 0, 1, 1, 0,
                0, 1, 1, 1, 1, 0, 0,
                0, 1, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getUnifiedKeyTest() {
        boolean[] leftIn = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 0
        });
        boolean[] rightIn = Utils.translateToBinaryArray(new byte[]{
                1, 1, 0, 0, 1, 1, 0,
                0, 1, 1, 1, 1, 0, 0,
                0, 1, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 0
        });
        boolean[] out = DesKey.getUnifiedKey(leftIn, rightIn);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                0, 1, 1, 1, 1, 0, 0,
                0, 1, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getFinallyPermutedKeyTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                0, 1, 1, 1, 1, 0, 0,
                0, 1, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 0
        });
        boolean[] out = DesKey.getFinallyPermutedKey(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0, 0, 0,
                0, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 1, 1, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1,
                0, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 0, 0, 0, 0, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void desKeyTest() {
        DesKey desKey = new DesKey(3544952156018063160l);

        boolean[] key1test = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0, 0, 0,
                0, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 1, 1, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1,
                0, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 0, 0, 0, 0, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(0), key1test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(15), key1test));

        boolean[] key2test = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0, 0, 0,
                1, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 0, 1, 0, 0,
                0, 1, 0, 1, 0, 0, 0, 0,
                1, 0, 1, 0, 0, 0, 1, 1,
                0, 1, 0, 0, 0, 1, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(1), key2test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(14), key2test));

        boolean[] key3test = Utils.translateToBinaryArray(new byte[]{
                1, 1, 0, 1, 0, 0, 0, 0,
                1, 0, 1, 0, 1, 1, 0, 0,
                0, 0, 1, 0, 0, 1, 1, 0,
                1, 1, 1, 1, 0, 1, 1, 0,
                1, 0, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 1, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(2), key3test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(13), key3test));

        boolean[] key4test = Utils.translateToBinaryArray(new byte[]{
                1, 1, 1, 0, 0, 0, 0, 0,
                1, 0, 1, 0, 0, 1, 1, 0,
                0, 0, 1, 0, 0, 1, 1, 0,
                0, 1, 0, 0, 1, 0, 0, 0,
                0, 0, 1, 1, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 0, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(3), key4test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(12), key4test));

        boolean[] key5test = Utils.translateToBinaryArray(new byte[]{
                1, 1, 1, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 0, 1, 1, 0,
                0, 0, 1, 0, 0, 1, 1, 0,
                0, 0, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 1, 0, 1, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(4), key5test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(11), key5test));

        boolean[] key6test = Utils.translateToBinaryArray(new byte[]{
                1, 1, 1, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 0, 0, 1, 0,
                0, 1, 1, 1, 0, 0, 1, 0,
                0, 1, 1, 0, 0, 0, 1, 0,
                0, 1, 0, 1, 1, 1, 0, 1,
                0, 1, 1, 0, 0, 0, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(5), key6test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(10), key6test));

        boolean[] key7test = Utils.translateToBinaryArray(new byte[]{
                1, 0, 1, 0, 0, 1, 0, 0,
                1, 1, 0, 1, 0, 0, 1, 0,
                0, 1, 1, 1, 0, 0, 1, 0,
                1, 0, 0, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 1, 0, 0, 1,
                0, 0, 1, 1, 1, 0, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(6), key7test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(9), key7test));

        boolean[] key8test = Utils.translateToBinaryArray(new byte[]{
                1, 0, 1, 0, 0, 1, 1, 0,
                0, 1, 0, 1, 0, 0, 1, 1,
                0, 1, 0, 1, 0, 0, 1, 0,
                1, 1, 1, 0, 0, 1, 0, 1,
                0, 1, 0, 1, 1, 1, 1, 0,
                0, 1, 0, 1, 0, 0, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(7), key8test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(8), key8test));

        boolean[] key9test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 1, 0, 0, 1, 1, 0,
                0, 1, 0, 1, 0, 0, 1, 1,
                0, 1, 0, 1, 0, 0, 1, 1,
                1, 1, 0, 0, 1, 0, 1, 1,
                1, 0, 0, 1, 1, 0, 1, 0,
                0, 1, 0, 0, 0, 0, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(8), key9test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(7), key9test));

        boolean[] key10test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 1, 0, 1, 1, 1, 1,
                0, 1, 0, 1, 0, 0, 0, 1,
                0, 1, 0, 1, 0, 0, 0, 1,
                1, 1, 0, 1, 0, 0, 0, 0,
                1, 1, 0, 0, 0, 1, 1, 1,
                0, 0, 1, 1, 1, 1, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(9), key10test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(6), key10test));

        boolean[] key11test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 1, 1, 1, 1,
                0, 1, 0, 0, 0, 0, 0, 1,
                1, 1, 0, 1, 1, 0, 0, 1,
                0, 0, 0, 1, 1, 0, 0, 1,
                0, 0, 0, 1, 1, 1, 1, 0,
                1, 0, 0, 0, 1, 1, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(10), key11test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(5), key11test));

        boolean[] key12test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 1, 1, 1, 1, 1,
                0, 1, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 1, 1, 0, 0, 1,
                1, 1, 0, 1, 1, 0, 0, 0,
                0, 1, 1, 1, 0, 0, 0, 0,
                1, 0, 1, 1, 0, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(11), key12test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(4), key12test));

        boolean[] key13test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 1, 1, 1, 1, 1,
                0, 0, 0, 0, 1, 0, 0, 1,
                1, 0, 0, 0, 1, 0, 0, 1,
                0, 0, 1, 0, 0, 0, 1, 1,
                0, 1, 1, 0, 1, 0, 1, 0,
                0, 0, 1, 0, 1, 1, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(12), key13test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(3), key13test));

        boolean[] key14test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 1, 1, 0, 1, 1,
                0, 0, 1, 0, 1, 0, 0, 0,
                1, 0, 0, 0, 1, 1, 0, 1,
                1, 0, 1, 1, 0, 0, 1, 0,
                0, 0, 1, 1, 1, 0, 0, 1,
                1, 0, 0, 1, 0, 0, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(13), key14test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(2), key14test));

        boolean[] key15test = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 1, 1, 0, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 0, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 0, 1, 1,
                0, 0, 1, 1, 0, 1, 1, 1
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(14), key15test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(1), key15test));

        boolean[] key16test = Utils.translateToBinaryArray(new byte[]{
                0, 1, 0, 1, 0, 0, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 0,
                1, 0, 0, 0, 1, 1, 0, 0,
                1, 0, 1, 0, 0, 1, 1, 1,
                0, 1, 0, 0, 0, 0, 1, 1,
                1, 1, 0, 0, 0, 0, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(desKey.getEncodeKeys().get(15), key16test));
        Preconditions.checkArgument(Arrays.equals(desKey.getDecodeKeys().get(0), key16test));
    }
}