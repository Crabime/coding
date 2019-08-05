package cn.crabime;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class CBlockingQueueTest extends TestCase {

    private static CBlockingQueue queue;

    private static final int BASIC_SEED = 1000;

    private Timer producerTimer = new Timer();
    private Timer consumerTimer = new Timer();

    @Override
    protected void setUp() {
        queue = new CBlockingQueue(2);
    }

    @Test
    public void testInsertAndGetItemsFromBlockingQueue() throws InterruptedException {
        producerTimer.schedule(new Producer(), 0, 1000);
        consumerTimer.schedule(new Consumer(), 3000, 1000);
        Thread.sleep(10000);
    }

    @Test
    public void testMultiInsertAndGetFromBlockingQueue() throws InterruptedException {
        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer();
            producerTimer.schedule(producers[i], 0, 1000);
        }

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer();
            consumerTimer.schedule(consumers[i], 0, 1000);
        }

        Thread.sleep(10000);
    }

    private static class Producer extends TimerTask {
        @Override
        public void run() {
            int number = MillionNumberGenerator.generateSingleNumber(BASIC_SEED);

            queue.put(number);
            System.out.println(Thread.currentThread().getName() + "生产数字" + number +
                    "当前大小为：" + queue.remainingCapacity());
        }
    }

    private static class  Consumer extends TimerTask {
        @Override
        public void run() {
            Object result = queue.take();
            System.out.println(Thread.currentThread().getName() + "取出数字" + result +
                    "当前大小为：" + queue.remainingCapacity());
        }
    }
}
