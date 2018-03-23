package cn.crabime.practice;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by crabime on 1/10/18.
 */
public class FairLock implements Runnable {
    private static ReentrantLock fairLock = new ReentrantLock(true);

    public void run() {
        while (true){
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + ":获得锁");
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread one = new Thread(fairLock);
        Thread two = new Thread(fairLock);
        one.start();
        two.start();
    }
}
