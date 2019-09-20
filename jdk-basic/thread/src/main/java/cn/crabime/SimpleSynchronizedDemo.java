package cn.crabime;

/**
 * 通过javap查看synchronized方法对应处理指令
 */
public class SimpleSynchronizedDemo implements SimpleAccountOps {

    private int account;

    private final static Object lock = new Object();

    @Override
    public int incAccount(int num) {
        synchronized (lock) {
            account += num;
            return account;
        }
    }

    @Override
    public int decAccount(int num) {
        synchronized (lock) {
            if (account >= num) {
                account -= num;
            }
            return account;
        }
    }

    public static void main(String[] args) {

        final SimpleAccountOps ops = new SimpleSynchronizedDemo();
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
