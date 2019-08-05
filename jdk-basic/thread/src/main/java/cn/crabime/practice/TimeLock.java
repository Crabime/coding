package cn.crabime.practice;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by crabime on 1/10/18.
 */
public class TimeLock implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();

    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);  // 故意让该线程睡眠6s,第二个线程因为无法获取到锁而返回false
            }else {
                System.out.println("take lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread thread = new Thread(timeLock);
        Thread thread1 = new Thread(timeLock);
        thread.start();
        thread1.start();
    }
}
