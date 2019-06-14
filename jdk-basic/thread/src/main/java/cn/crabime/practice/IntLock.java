package cn.crabime.practice;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by crabime on 1/10/18.
 */
public class IntLock implements Runnable{
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private int id;
    public IntLock(int id) {
        this.id = id;
    }

    public void run() {
        try {
            if (id == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if (lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock first = new IntLock(1);
        IntLock second = new IntLock(2);
        Thread thread1 = new Thread(first);
        Thread thread2 = new Thread(second);
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread2.interrupt();
    }
}
