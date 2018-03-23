package cn.crabime.practice;

/**
 * Created by crabime on 1/7/18.
 */
public class NoVisibility {

    private static boolean ready;

    private static int number;

    static class ReaderThread extends Thread{
        @Override
        public void run() {
            while (!ready);
            System.out.println("当前number值为:" + number);

        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);

        number = 42;
        ready = true;

        Thread.sleep(5000);
    }
}
