package cn.crabime.practice;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by crabime on 1/7/18.
 * 重入锁的使用
 */
public class ReenterLock {

    private static ReentrantLock lock = new ReentrantLock();

    private static int i = 0;

    static class IncreaseThread extends Thread{
        void increase(){
            lock.lock();
            try {
                i++;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++){
                increase();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread increase1 = new IncreaseThread();
        Thread increase2 = new IncreaseThread();
        increase1.start();
        increase2.start();
        increase1.join();
        increase2.join();
        System.out.println(i);
    }
}
