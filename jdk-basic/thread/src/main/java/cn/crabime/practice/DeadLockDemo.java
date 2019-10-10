package cn.crabime.practice;

/**
 * 死锁代码
 * @author crabime
 * create at 2019/10/10
 */
public class DeadLockDemo {

    private final A a = new A();

    private final B b = new B();

    class A implements Runnable {

        protected void mA(B b1) {
            synchronized (a) {
                System.out.println("开始执行mA方法");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    b1.mB(a);
                }
            }
        }

        protected void say(String words) {
            System.out.println("A说:" + words);
        }

        @Override
        public void run() {
            B nb = new B();
            mA(nb);
        }
    }

    class B implements Runnable {

        protected void mB(A a1) {
            synchronized (b) {
                System.out.println("开始执行mB方法");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    a1.say("hello");
                }
            }
        }

        @Override
        public void run() {
            A na = new A();
            mB(na);
        }
    }

    private void start() {
        Thread t1 = new Thread(new A(), "线程A");
        Thread t2 = new Thread(new B(), "线程B");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        DeadLockDemo demo = new DeadLockDemo();
        demo.start();
    }
}
