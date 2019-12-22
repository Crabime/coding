package cn.crabime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java中wait与sleep区别
 * @author crabime
 * create at 19/10/10
 */
public class WaitAndSleep {

    private final static Logger logger = LoggerFactory.getLogger(WaitAndSleep.class);

    /**
     * 接受wait/notify消息对象
     */
    private final Object lock = new Object();

    protected StubObj obj = new StubObj();

    private volatile boolean emptySign = true;

    private void startWork() {
        Thread t1 = new Thread(new Worker(), "工作线程一");
        t1.start();
    }

    private void startProducerThenConsumer() {
        Thread t1 = new Thread(new Producer(), "生产者");
        Thread t2 = new Thread(new Consumer(), "消费者一");
        // 通过notify应该直接唤醒一个线程
        Thread t3 = new Thread(new Consumer(), "消费者二");
        t1.start();
        t2.start();
        t3.start();
    }

    public static void main(String[] args) {
        WaitAndSleep was = new WaitAndSleep();
        if (args.length > 0) {
            int order = Integer.parseInt(args[0]);
            if (order == 1) {
                was.startWork();
            } else {
                was.startProducerThenConsumer();
            }
        }

    }

    class StubObj {
        private int index = 0;

        protected void inc() {
            index++;
        }

        protected void dec() {
            index--;
        }

        protected int getIndex() {
            return index;
        }
    }

    /**
     * 线程在sleep时不会释放锁，这里打印出的堆栈如下：
     *
     * "工作线程一" #11 prio=5 os_prio=0 tid=0x0000000018e01800 nid=0x14274 waiting on condition [0x000000001981e000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     *         at java.lang.Thread.sleep(Native Method)
     *         at cn.crabime.WaitAndSleep$Worker.run(WaitAndSleep.java:36)
     *         - locked <0x00000000d64c2460> (a java.lang.Object)
     *         at java.lang.Thread.run(Thread.java:748)
     */
    class Worker implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                logger.info("模拟开始执行任务...");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info("模拟任务结束...");
            }
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                if (!emptySign) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        logger.error("出错了", e);
                    }
                }
                emptySign = false;
                logger.info("通知消费者线程开始消费了，这里我还要先休息3s");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.error("睡眠过程中被打断了，fuck!", e);
                }
                lock.notifyAll();
                obj.inc();
                logger.info("当前index=" + obj.getIndex());
                logger.info("模拟生产者线程执行完任务后注销过程，该过程持续2s");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error("生产者线程注销过程被打断了，fuck fuck!", e);
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                if (emptySign) {
                    try {
                        lock.wait(3000);
                        logger.info("我的等待到极限了");
                    } catch (InterruptedException e) {
                        logger.error("出错了", e);
                    }
                }
                emptySign = true;
                obj.dec();
                logger.info("当前index=" + obj.getIndex());
                lock.notify();
            }
        }
    }
}
