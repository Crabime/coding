package cn.crabime;

import java.util.concurrent.*;

/**
 * 有界阻塞队列线程池
 */
public class FixedBoundThreadPool {

    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(1, 5, 2000, TimeUnit.MILLISECONDS, blockingQueue, new ThreadPoolExecutor.AbortPolicy());

        // 设置允许线程池无任务时核心线程可失效策略
        executor.allowCoreThreadTimeOut(true);

        // 这里创建100个线程，多余的线程将会被抛弃
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "正在执行任务");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "任务执行结束");

            });
        }
        executor.shutdown();
    }
}
