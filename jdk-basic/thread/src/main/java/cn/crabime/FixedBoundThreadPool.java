package cn.crabime;

import java.util.concurrent.*;

/**
 * Created by crabime on 5/17/18.
 * 有界阻塞队列线程池
 */
public class FixedBoundThreadPool {
    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(10);
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(1, 5, 200, TimeUnit.MILLISECONDS, blockingQueue, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行任务");
                    try {
                        Thread.sleep(200);
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
        executor.shutdown();
    }
}
