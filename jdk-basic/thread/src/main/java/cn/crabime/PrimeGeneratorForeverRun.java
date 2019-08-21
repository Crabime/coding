package cn.crabime;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class PrimeGeneratorForeverRun implements Callable<BlockingQueue<BigInteger>> {

    private final BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(5);
    private volatile boolean cancelled = false;//退出条件
    private static BigInteger b = BigInteger.ONE;

    @Override
    public BlockingQueue<BigInteger> call() throws Exception {
        System.out.println("Begin Time: " + new Date());
        while (!cancelled) {
            TimeUnit.SECONDS.sleep(1);
            synchronized (this) {
                b = b.nextProbablePrime();
                primes.offer(b);
            }
        }
        System.out.println("End Time: " + new Date());
        return primes;
    }

    public void cancel() {//当外部调用这个方法时，将时call中的while循环退出
        cancelled = true;
    }
}
