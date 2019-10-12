package cn.crabime.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 三个线程循环串行执行，使用wait／notify模型
 * Thread1 -> Thread2 -> Thread3
 */
public class ThreadExecuteInOrder {

    private final static Logger logger = LoggerFactory.getLogger(ThreadExecuteInOrder.class);

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        ThreadC threadC = new ThreadC();
        threadA.setThreadB(threadB);
        threadB.setThreadC(threadC);
        threadC.setThreadA(threadA);
        Thread t1 = new Thread(threadA, "线程一");
        Thread t2 = new Thread(threadB, "线程二");
        Thread t3 = new Thread(threadC, "线程三");
        t1.start();
        t2.start();
        t3.start();
    }

    static class ThreadA implements Runnable {

        private ThreadB threadB;

        public void setThreadB(ThreadB threadB) {
            this.threadB = threadB;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (threadB) {
                    synchronized (this) {

                        logger.info("开始运行");
                        this.notify();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.info("执行结束");
                    }

                    try {
                        // 这里wait会释放已经拿到的锁
                        threadB.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class ThreadB implements Runnable {

        private ThreadC threadC;

        public void setThreadC(ThreadC threadC) {
            this.threadC = threadC;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (threadC) {
                    synchronized (this) {

                        logger.info("开始运行");
                        this.notify();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.info("执行结束");
                    }

                    try {
                        threadC.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class ThreadC implements Runnable {

        private ThreadA threadA;

        public void setThreadA(ThreadA threadA) {
            this.threadA = threadA;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (threadA) {
                    synchronized (this) {

                        logger.info("开始运行");
                        this.notify();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.info("执行结束");
                    }

                    try {
                        threadA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
