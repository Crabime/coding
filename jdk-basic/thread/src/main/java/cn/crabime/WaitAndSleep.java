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

    protected final Object lock = new Object();

    protected StubObj obj = new StubObj();

    private boolean sign = false;

    private void startWork() {
        Thread t1 = new Thread(new Worker(), "工作线程一");
        t1.start();
    }

    private void startProducerThenConsumer() {
        Thread t1 = new Thread(new Producer(), "生产者");
        Thread t2 = new Thread(new Consumer(), "消费者");
        t1.start();
        t2.start();

        try {
            Thread.sleep(3000);
            t1.interrupt();
            t2.interrupt();
            t1.join();
            t2.join();
            int index = obj.getIndex();
            System.out.println("当前值为：" + index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                while (!sign) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                sign = true;
                obj.inc();
                lock.notify();
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while (sign) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                sign = false;
                obj.dec();
                lock.notify();
            }
        }
    }
}
