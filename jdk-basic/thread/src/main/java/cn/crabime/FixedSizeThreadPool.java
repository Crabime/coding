package cn.crabime;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by crabime on 5/17/18.
 */
public class FixedSizeThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor fixedPoolExecutor = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, new CBlockingQueue<Runnable>(10));
        for (int i = 0; i < 100; i++) {
            fixedPoolExecutor.submit(new Runnable() {
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
            System.out.println("当前ThreadPoolExecutor中corePoolSize" + fixedPoolExecutor.getCorePoolSize() + "\n"
                + "最大线程数量: " + fixedPoolExecutor.getMaximumPoolSize() + "\n"
                + "工作队列中执行任务的长度为 : " + fixedPoolExecutor.getQueue().size());
        }
        fixedPoolExecutor.shutdown();
    }
}
