package cn.crabime;

/**
 * Created by crabime on 12/18/17.
 * 银行账户执行存、取钱操作
 */
public class Bank {
    private int count = 0; //账户余额

    public synchronized void addMoney(int money){
        count += money;
        System.out.println(System.currentTimeMillis() + "存进" + money);
    }

    public synchronized void subMoney(int money){
        if (count - money < 0){
            System.out.println("余额不足");
            return;
        }
        count -= money;
        System.out.println(System.currentTimeMillis() + "取出" + money);
    }

    public void lookMoney(){
        System.out.println("账户余额" + count);
    }
}
