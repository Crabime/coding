package cn.crabime;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * jdk blocking queue测试
 */
public class ArrayBlockingQueueTest extends TestCase {

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


        Thread.sleep(10000);
    }
}
