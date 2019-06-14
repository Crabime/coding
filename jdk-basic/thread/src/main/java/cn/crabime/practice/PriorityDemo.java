package cn.crabime.practice;

/**
 * Created by crabime on 1/7/18.
 * 线程优先级
 */
public class PriorityDemo {
    static class HighPriority extends Thread{
        static int count = 0;
        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if (count>100000){
                        System.out.println("High priority has completed");
                        break;
                    }
                }
            }
        }
    }

    static class LowPriority extends Thread{
        static int num = 0;
        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    num++;
                    if (num>100000){
                        System.out.println("Low priority has completed");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new HighPriority();
        Thread low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        high.start();
        low.start();
    }
}
