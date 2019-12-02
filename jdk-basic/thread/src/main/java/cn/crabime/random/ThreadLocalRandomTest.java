package cn.crabime.random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {

    private static Logger logger = LoggerFactory.getLogger(ThreadLocalRandomTest.class);

    volatile int count = 0;

    private volatile boolean flag = true;

    private Random random = new Random();

    private ThreadLocalRandom tlr = ThreadLocalRandom.current();

    private void generateRandomVal() {
        int i = random.nextInt(100);
        logger.info("当前生成随机数为{}", i);
        count++;
    }

    private void generateRandomValInThreadLocal() {
        int i = tlr.nextInt(100);
        logger.info("当前生成随机数为{}", i);
        count++;
    }

    public static void main(String[] args) {
        ThreadLocalRandomTest test = new ThreadLocalRandomTest();
        ThreadLocalRandomTest.RandomRunnable tl = test.new RandomRunnable();
        int size = 10;

        Thread[] t = new Thread[size];
        for (int i = 0; i < size; i++) {
            t[i] = new Thread(tl, "线程" + i);
        }
        for (int i = 0; i < size; i++) {
            t[i].start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.error("主程序睡眠中断", e);
        }
        test.flag = false;
        logger.info("生成随机数总数为{}", test.count);
    }

    class RandomRunnable implements Runnable {

        @Override
        public void run() {
            while (flag) {
                generateRandomVal();
                // generateRandomValInThreadLocal();
            }
        }
    }
}
