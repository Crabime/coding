package cn.crabime.practice;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by crabime on 1/10/18.
 */
public class TryLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TryLock(int lock) {
        this.lock = lock;
    }

    public void run() {
        if (lock == 1){
            while (true){
                if (lock1.tryLock()){
                    try {
                        Thread.sleep(500);
                        if (lock2.tryLock()){  // 当时没有拿到锁就直接返回
                            System.out.println(Thread.currentThread().getId() + ":My Job done");
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (lock2.isHeldByCurrentThread())
                            lock2.unlock();
                        if (lock1.isHeldByCurrentThread())
                            lock1.unlock();
                    }
                }
            }
        }else {
            while (true){
                if (lock2.tryLock()){
                    try {
                        Thread.sleep(500);
                        if (lock1.tryLock()){
                            System.out.println(Thread.currentThread().getId() + ":My Job done");
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (lock1.isHeldByCurrentThread())
                            lock1.unlock();
                        if (lock2.isHeldByCurrentThread())
                            lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock tryLock1 = new TryLock(1);
        TryLock tryLock2 = new TryLock(2);
        Thread threadOne = new Thread(tryLock1);
        Thread threadTwo = new Thread(tryLock2);
        threadOne.start();
        threadTwo.start();
    }
}
