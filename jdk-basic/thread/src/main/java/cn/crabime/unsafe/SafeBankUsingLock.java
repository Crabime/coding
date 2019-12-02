package cn.crabime.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeBankUsingLock implements BankInterface {

    private final static Logger logger = LoggerFactory.getLogger(SafeBankUsingLock.class);

    private int count = 0;

    private Lock lock = new ReentrantLock();

    @Override
    public void addMoney(int money) {
        try {
            lock.lock();
            count += money;
            logger.info("存进{}，余额{}", money, count);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void subMoney(int money) {
        try {
            lock.lock();
            if (count - money < 0){
                logger.info("余额不足，无法取钱.");
                return;
            }
            count -= money;
            logger.info("取出{}，余额{}", money, count);
        } finally {
            lock.unlock();
        }
    }
}
