package cn.crabime;

/**
 * Created by crabime on 12/18/17.
 * 如果不采用任何线程同步方式对某一个变量进行读写操作,很容易引起错误
 */
public class SyncThreadTest {
    final static Bank bank = new Bank();

    public static void main(String[] args) {
        Thread add = new AddMoneyThread("存钱线程");
        Thread add1 = new AddMoneyThread("存钱线程一");
        Thread sub = new SubMoneyThread("取钱线程");
        Thread sub1 = new AddMoneyThread("取钱线程一");
        add1.start();
        add.start();
        sub1.start();
        sub.start();
    }

    static class SubMoneyThread extends Thread {

        public SubMoneyThread(String name) {
            super(name);
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bank.subMoney(100);
                bank.lookMoney();
                System.out.println("\n");
            }
        }
    }

    static class AddMoneyThread extends Thread {

        public AddMoneyThread(String name) {
            super(name);
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bank.addMoney(100);
                bank.lookMoney();
                System.out.println("\n");
            }
        }
    }
}
