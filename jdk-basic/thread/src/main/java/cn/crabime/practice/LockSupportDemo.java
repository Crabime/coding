package cn.crabime.practice;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by crabime on 1/24/18.
 */
public class LockSupportDemo {

    public static Object object = new Object();

    static ChangeObject object1 = new ChangeObject("t1");
    static ChangeObject object2 = new ChangeObject("t2");

    public static class ChangeObject extends Thread{
        public ChangeObject(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object){
                System.out.println("in" + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        object1.start();
        Thread.sleep(1000);
        object2.start();
        LockSupport.unpark(object1);
        LockSupport.unpark(object2);
        object1.join();
        object2.join();
    }
}
