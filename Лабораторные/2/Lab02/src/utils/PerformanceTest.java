package utils;

import main.DesEncoder;
import main.DesKey;

import java.io.IOException;
import java.util.Random;

/**
 * @author vladislav.trofimov@emc.com
 */
public class PerformanceTest {

    public static void main(String[] args) throws IOException {

        long before = System.currentTimeMillis();

        performTest(983040);

        long after = System.currentTimeMillis();

        System.out.println(after - before);

        PerformanceTest performanceTest = new PerformanceTest();

        TestThread testThread1 = performanceTest.new TestThread();
        TestThread testThread2 = performanceTest.new TestThread();
        TestThread testThread3 = performanceTest.new TestThread();
        TestThread testThread4 = performanceTest.new TestThread();

        before = System.currentTimeMillis();

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

        after = System.currentTimeMillis();

        System.out.println(after - before);

    }

    public static void performTest(int iterations) {

        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            byte[] outTest = new byte[8];
            random.nextBytes(outTest);
            DesKey desKey = new DesKey(random.nextLong());
            byte[] encoded = DesEncoder.encodeBlock(outTest, desKey.getEncodeKeys());
        }

    }

    class TestThread extends Thread {

        public void run() {
            performTest(245760);
        }

    }

}
