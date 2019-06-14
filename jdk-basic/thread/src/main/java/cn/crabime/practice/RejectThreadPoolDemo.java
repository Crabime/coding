package cn.crabime.practice;

import java.util.concurrent.*;

/**
 * Created by crabime on 1/25/18.
 */
public class RejectThreadPoolDemo {

    private static class MyTask implements Runnable {

        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread Id" +
                    Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discard!");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++){
            executorService.submit(myTask);
            Thread.sleep(10);
        }
    }
}
