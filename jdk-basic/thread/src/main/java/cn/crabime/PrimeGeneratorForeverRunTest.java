package cn.crabime;

import java.math.BigInteger;
import java.util.concurrent.*;

public class PrimeGeneratorForeverRunTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        PrimeGeneratorForeverRun primeGenerator = new PrimeGeneratorForeverRun();//定义一个单例的质素生成器
        ExecutorService service = Executors.newFixedThreadPool(2);//定义线程池的大小为2
        Future<BlockingQueue<BigInteger>> result1 = service.submit(primeGenerator);//提交任务
        Future<BlockingQueue<BigInteger>> result2 = service.submit(primeGenerator);
        try {
            TimeUnit.SECONDS.sleep(10);//守护线程沉睡10秒，目的是向在10秒内让任务1，2不停的生成质素
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            primeGenerator.cancel();//停止生成质素
            service.shutdown();//关闭线程池，这里使用平滑关闭，目的是不强制退出任何线程
            try {
                BlockingQueue<BigInteger> list = result1.get();//获取第一个任务生成的所有质素
                for (BigInteger bigInteger : list) {
                    System.out.print(bigInteger + ",");
                }
                System.out.println();
                list = result2.get();
                for (BigInteger bigInteger : list) {//获取第二个任务生成的所有质素
                    System.out.print(bigInteger + ",");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
