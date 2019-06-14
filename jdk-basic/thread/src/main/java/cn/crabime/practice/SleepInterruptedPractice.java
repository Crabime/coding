package cn.crabime.practice;

/**
 * Created by crabime on 1/7/18.
 * 线程Sleep过程中如果被interrupt会抛出异常
 */
public class SleepInterruptedPractice {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true){
                    if (Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted while sleep");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
