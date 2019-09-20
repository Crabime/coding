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
            try {
                // 模拟事务耗时
                Thread.sleep(30000);
            } catch (InterruptedException e) {

            }
            return account;
        }
    }

    @Override
    public int decAccount(int num) {
        synchronized (lock) {
            if (account >= num) {
                account -= num;
            }
            try {
                // 模拟事务耗时
                Thread.sleep(20000);
            } catch (InterruptedException e) {

            }
            return account;
        }
    }

    public static void main(String[] args) {

        final SimpleAccountOps ops = new SimpleSynchronizedDemo();

        Thread thread1 = new SimpleAccountThread("线程一", ops, SimpleAccountType.INC);
        Thread thread2 = new SimpleAccountThread("线程二", ops, SimpleAccountType.DEC);
        thread1.start();
        thread2.start();
    }
}
