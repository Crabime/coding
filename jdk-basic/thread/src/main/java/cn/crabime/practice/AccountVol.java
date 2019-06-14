package cn.crabime.practice;

/**
 * Created by crabime on 1/7/18.
 */
public class AccountVol {
    static volatile int i = 0;
    static class IncreaseThread extends Thread{
        void increase(){
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
        Thread increaseOne = new IncreaseThread();
        Thread increaseTwo = new IncreaseThread();
        increaseOne.start();
        increaseTwo.start();
        increaseOne.join();
        increaseTwo.join();
        System.out.println(i);
    }
}
