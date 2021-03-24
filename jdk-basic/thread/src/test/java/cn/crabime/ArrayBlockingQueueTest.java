package cn.crabime;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * jdk blocking queue测试
 */
public class ArrayBlockingQueueTest extends TestCase {

    private final static Logger logger = LoggerFactory.getLogger(ArrayBlockingQueueTest.class);

    private BlockingQueue<Integer> queue;

    @Override
    protected void setUp() throws Exception {
        queue = new ArrayBlockingQueue<>(3);
        queue.put(1);
        queue.put(2);
        queue.put(3);
    }

    @Test
    public void testTake() throws InterruptedException {
        assertEquals(1, queue.take().intValue());
    }

    @Test
    public void testAddIntoQueueOutOfCapacity() {
        for (int i = 0; i < 3; i++) {
            try {
                queue.add(i);
                fail("queue full exception");
            } catch (IllegalStateException e) {

            }
        }
    }

    @Test
    public void testTakeIntoQueueOutOfCapacity() {
        assertFalse(queue.offer(5));
    }

    /**
     * 使用Timer测试jdk阻塞队列效果
     * @throws InterruptedException
     */
    @Test
    public void testMultiInsertAndGetFromBlockingQueue() throws InterruptedException {
        queue.clear();
        Random r = new Random();
        Thread[] producers = new Thread[5];
        Thread[] consumers = new Thread[3];
        for (int i = 0; i < producers.length; i++) {
            String pName = "producer" + i;
            producers[i] = new Thread(() -> {
                int val = r.nextInt(10);
                logger.info("simulate producer start and add value {}", val);
                System.out.println(Thread.currentThread().getName() + "-" + System.currentTimeMillis());
                queue.add(val);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("simulate producer end!");
            }, pName);
            producers[i].start();
        }

        for (int i = 0; i < consumers.length; i++) {
            String cName = "consumer" + i;
            consumers[i] = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() +  "-" + System.currentTimeMillis());
                    int val = queue.take();
                    logger.info("simulate consumer start take value {}", val);
                    Thread.sleep(5000);
                    logger.info("simulate consumer end!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, cName);
            consumers[i].start();
        }

        Thread.sleep(5000);
    }


}
