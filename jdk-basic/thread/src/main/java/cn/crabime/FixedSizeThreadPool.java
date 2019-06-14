package cn.crabime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by crabime on 5/17/18.
 */
public class FixedSizeThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "任务执行结束");
                }
            });
            System.out.println("当前ThreadPoolExecutor中corePoolSize" + executor.getCorePoolSize() + "\n"
                + "最大线程数量: " + executor.getMaximumPoolSize() + "\n"
                + "工作队列中执行任务的长度为 : " + executor.getQueue().size());
        }
        executorService.shutdown();
    }
}
