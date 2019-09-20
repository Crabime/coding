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

    }
}
