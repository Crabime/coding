package cn.crabime;

/**
 * Created by crabime on 12/18/17.
 */
public class SyncThreadTest {

    public static void main(String[] args) {
        final Bank bank = new Bank();

        Thread add = new Thread(new Runnable() {
            public void run() {
                while (true){
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
        });

        Thread sub = new Thread(new Runnable() {
            public void run() {
                while (true){
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
        });

        sub.start();
        add.start();
    }
}
