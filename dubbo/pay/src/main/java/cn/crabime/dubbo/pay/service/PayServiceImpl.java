package cn.crabime.dubbo.pay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayServiceImpl implements PayService {

    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public boolean decAccount(String username, int money) {
        logger.info("用户{}扣除{}成功", username, money);
        return false;
    }
}
