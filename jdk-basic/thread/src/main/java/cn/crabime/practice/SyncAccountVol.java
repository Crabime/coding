package cn.crabime.practice;

/**
 * Created by crabime on 1/7/18.
 */
public class SyncAccountVol {
    static volatile int i = 0;
    static class IncreaseThread extends Thread{
        static synchronized void increase(){
            i++;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++){
                increase();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread increase1 = new IncreaseThread();
        Thread increase2 = new IncreaseThread();
        increase1.start();
        increase2.start();
        increase1.join();
        increase2.join();
        System.out.println(i);
    }
}
