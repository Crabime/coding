package cn.crabime.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by crabime on 12/18/17.
 * 如果不采用任何线程同步方式对某一个变量进行读写操作,很容易引起错误
 *
 * modified by crabime on 12/01/19
 * 增加多种线程安全控制
 */
public class BankMain {

    private final static Logger logger = LoggerFactory.getLogger(BankMain.class);

    private final static Random random = new Random();

    /*
     * 线程中断标示位
     */
    static boolean flag = true;

    private void startUnsafe(BankInterface bankInterface) {
        Thread add = new AddMoneyThread("存钱线程", bankInterface);
        Thread add1 = new AddMoneyThread("存钱线程一", bankInterface);
        Thread sub = new SubMoneyThread("取钱线程", bankInterface);
        Thread sub1 = new SubMoneyThread("取钱线程一", bankInterface);
        add1.start();
        add.start();
        sub1.start();
        sub.start();
    }

    public static void main(String[] args) {
        int sign = -1;
        BankInterface bankInterface;
        if (args != null && args.length == 1) {
            sign = Integer.parseInt(args[0]);
        }
        switch (sign){
            case 0:
               bankInterface = new UnSafeBank();
               break;
            case 1:
                bankInterface = new SafeBank();
                break;
            case 3:
                bankInterface = new SafeBankUsingLock();
                break;
           default:
               logger.error("jvm参数传入不合理");
               throw new IllegalArgumentException("命令行参数传入错误");
        }
        BankMain bankMain = new BankMain();
        bankMain.startUnsafe(bankInterface);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
    }

    abstract static class BaseMoneyThread extends Thread {
        private BankInterface bankInterface;

        public BaseMoneyThread(String name, BankInterface bankInterface) {
            super(name);
            this.bankInterface = bankInterface;
        }
    }

    static class SubMoneyThread extends BaseMoneyThread {

        public SubMoneyThread(String name, BankInterface bankInterface) {
            super(name, bankInterface);
        }

        public void run() {
            while (flag) {
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.bankInterface.subMoney(100);
            }
        }
    }

    static class AddMoneyThread extends BaseMoneyThread {

        public AddMoneyThread(String name, BankInterface bankInterface) {
            super(name, bankInterface);
        }

        public void run() {
            while (flag) {
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.bankInterface.addMoney(100);
            }
        }
    }
}
