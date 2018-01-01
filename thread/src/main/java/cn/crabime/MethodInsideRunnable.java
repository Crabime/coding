package cn.crabime;

/**
 * Created by crabime on 1/1/18.
 * 在runnable中的局部变量是否是安全的
 * 结果:每个线程各有一个独立的变量,相互之间互不干扰
 * 每一个线程内部的类变量或实例变量也是线程安全的
 */
public class MethodInsideRunnable {

    public static void main(String[] args) {
        InnerRunnable runnable1 = new InnerRunnable("Thread-1 ");
        InnerRunnable runnable2 = new InnerRunnable("Thread-2 ");
        InnerRunnable runnable3 = new InnerRunnable("Thread-3 ");
        runnable1.start();
        runnable2.start();
        runnable3.start();
    }

    private static class InnerRunnable extends Thread{

        private int counter = 1;

        public InnerRunnable(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                increase();
                System.out.println(this.getName() + counter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private int increase(){
            return counter++;
        }
    }
}
