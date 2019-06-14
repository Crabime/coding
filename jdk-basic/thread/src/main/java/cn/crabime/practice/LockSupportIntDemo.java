package cn.crabime.practice;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by crabime on 1/24/18.
 * LockSupport Interrupt Demo
 */
public class LockSupportIntDemo {

    private static Object object = new Object();

    static ChangeObject2 object1 = new ChangeObject2("t1");
    static ChangeObject2 object2 = new ChangeObject2("t2");

    public static class ChangeObject2 extends Thread{
        public ChangeObject2(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object){
                System.out.println("in" + getName());
                LockSupport.park();
                if (Thread.interrupted()){
                    System.out.println(getName() + "被中断了");
                }
            }
            System.out.println(getName() + "执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        object1.start();
        Thread.sleep(1000);
        object2.start();
        object1.interrupt();
        LockSupport.unpark(object2);
    }
}
