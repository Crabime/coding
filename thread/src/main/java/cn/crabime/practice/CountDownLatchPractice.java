package cn.crabime.practice;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by crabime on 1/24/18.
 * 使用CountDownLatch
 */
public class CountDownLatchPractice implements Runnable{

    static final CountDownLatch latch = new CountDownLatch(10);

    static final CountDownLatchPractice practice = new CountDownLatchPractice();

    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(practice);
        }

        //等待执行
        latch.await();
        System.out.println("Fire!");
        exec.shutdown();
    }
}
