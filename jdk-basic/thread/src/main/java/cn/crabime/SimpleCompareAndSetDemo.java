package cn.crabime;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * java代码中如何使用compareAndSet进行赋值操作
 */
public class SimpleCompareAndSetDemo implements SimpleAccountOps {

    private AtomicInteger account = new AtomicInteger();

    @Override
    public int incAccount(int ac) {
        return account.addAndGet(ac);
    }

    @Override
    public int decAccount(int dc) {
        int a = account.get();
        int decVal = a >= dc ? a - dc : 0;
        if (account.compareAndSet(a, decVal)) {
            return account.get();
        }
        return -1;
    }

    public static void main(String[] args) {
        SimpleAccountOps ops = new SimpleCompareAndSetDemo();
        long start = System.currentTimeMillis();
        Thread[] incThreads = new Thread[TOTAL_LENGTH];
        Thread[] decThreads = new Thread[TOTAL_LENGTH];
        for (int i = 0; i < TOTAL_LENGTH; i++) {
            incThreads[i] = new SimpleAccountThread("线程一", ops, SimpleAccountType.INC);
            decThreads[i] = new SimpleAccountThread("线程二", ops, SimpleAccountType.DEC);
            incThreads[i].start();
            decThreads[i].start();
            try {
                incThreads[i].join();
                decThreads[i].join();
            } catch (InterruptedException e) {

            }

        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时" + (end - start));
    }
}
