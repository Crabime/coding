package cn.crabime.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crabime on 12/18/17.
 * 银行账户执行存、取钱操作
 */
public class SafeBank implements BankInterface {
    private final static Logger logger = LoggerFactory.getLogger(SafeBank.class);

    private int count = 0; //账户余额

    @Override
    public synchronized void addMoney(int money){
        count += money;
        logger.info("存进{}，余额{}", money, count);
    }

    @Override
    public synchronized void subMoney(int money){
        if (count - money < 0){
            logger.info("余额不足，无法取钱.");
            return;
        }
        count -= money;
        logger.info("取出{}，余额{}", money, count);
    }
}
