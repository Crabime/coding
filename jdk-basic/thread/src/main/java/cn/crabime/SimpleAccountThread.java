package cn.crabime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SimpleAccountThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(SimpleAccountThread.class);

    private SimpleAccountOps simpleAccountOps;

    private String name;

    private Random random = new Random();

    private SimpleAccountType type;

    private final Object lock = new Object();

    public SimpleAccountThread(String name, SimpleAccountOps simpleAccountOps, SimpleAccountType type) {
        super(name);
        this.simpleAccountOps = simpleAccountOps;
        this.type = type;
    }

    private void incOps() {
        for (int i = 0; i < 10; i++) {
            int money = random.nextInt(30);
            synchronized (lock) {
                int account = this.simpleAccountOps.incAccount(money);
                logger.info("增加{},当前余额{}", money, account);
            }
        }
    }

    private void decOps() {
        for (int i = 0; i < 10; i++) {
            int money = random.nextInt(30);
            synchronized (lock) {
                int account = this.simpleAccountOps.decAccount(money);
                logger.info("减少{},当前余额{}", money, account);
            }
        }
    }

    @Override
    public void run() {
        if (this.type == SimpleAccountType.INC) {
            incOps();
        } else if (this.type == SimpleAccountType.DEC) {
            decOps();
        }
    }
}
