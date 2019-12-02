package cn.crabime.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crabime on 12/01/19.
 * 银行账户执行存、取钱操作(非安全操作)
 * 注意：自增与自减操作均为非原子性操作，无法保证线程安全性
 */
public class UnSafeBank implements BankInterface {
    private final static Logger logger = LoggerFactory.getLogger(UnSafeBank.class);

    private int count = 0; //账户余额

    @Override
    public void addMoney(int money){
        count += money;
        logger.info("存进{}，余额{}", money, count);
    }

    @Override
    public void subMoney(int money){
        if (count - money < 0){
            logger.info("余额不足，无法取钱.");
            return;
        }
        count -= money;
        logger.info("取出{}，余额{}", money, count);
    }
}
