package test;

import com.google.common.base.Preconditions;
import main.DesEncoder;
import main.DesKey;
import org.junit.Test;
import utils.Utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DesEncoderTest {

    @Test
    public void getLeftPartOfBlockTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        boolean[] out = DesEncoder.getLeftPartOfBlock(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void getRightPartOfBlockTest() {
        boolean[] in = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        boolean[] out = DesEncoder.getRightPartOfBlock(in);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 0, 0, 1, 1, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void xorBlocksTest() {
        boolean[] inLeft = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1
        });
        boolean[] function = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 0, 0, 0, 0, 1,
                0, 1, 0, 0, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 0, 1, 1,
                0, 0, 0, 1, 0, 0, 0, 1
        });
        boolean[] out = DesEncoder.xorBlocks(inLeft, function);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 1, 1, 0, 0, 1, 1,
                1, 0, 1, 0, 0, 0, 1, 1,
                0, 1, 0, 0, 0, 1, 0, 0
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void mergeBlocksTest() {
        boolean[] inLeft = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1
        });
        boolean[] inRight = Utils.translateToBinaryArray(new byte[]{
                1, 0, 0, 0, 0, 0, 0, 1,
                0, 1, 0, 0, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 0, 1, 1,
                0, 0, 0, 1, 0, 0, 0, 1
        });
        boolean[] out = DesEncoder.mergeBlocks(inLeft, inRight);
        boolean[] outTest = Utils.translateToBinaryArray(new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 1,
                0, 1, 0, 0, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 0, 1, 1,
                0, 0, 0, 1, 0, 0, 0, 1
        });
        Preconditions.checkArgument(Arrays.equals(out, outTest));
    }

    @Test
    public void encodeBlockTest() {

        TestThread testThread1 = new TestThread();
        TestThread testThread2 = new TestThread();
        TestThread testThread3 = new TestThread();
        TestThread testThread4 = new TestThread();

        testThread1.start();
        testThread2.start();
        testThread3.start();
        testThread4.start();

        try {
            testThread1.join();
            testThread2.join();
            testThread3.join();
            testThread4.join();
        } catch (InterruptedException e) {
            System.out.println("Error during multi-threading");
        }

    }

    class TestThread extends Thread {

        public void run() {

            Random random = new Random();

            for (int i = 0; i < 8192; i++) {
                byte[] outTest = new byte[8];
                random.nextBytes(outTest);
                DesKey desKey = new DesKey(random.nextLong());
                byte[] encoded = DesEncoder.encodeBlock(outTest, desKey.getEncodeKeys());
                byte[] out = DesEncoder.encodeBlock(encoded, desKey.getDecodeKeys());
                Preconditions.checkArgument(Arrays.equals(out, outTest));
            }

        }

    }

}
