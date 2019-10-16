package cn.crabime.dubbo.server.service.impl;

import cn.crabime.dubbo.pay.service.PayService;
import cn.crabime.dubbo.server.service.EnvelopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EnvelopeServiceImpl implements EnvelopeService, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(EnvelopeServiceImpl.class);

    private ApplicationContext applicationContext;

    @Override
    public void sendEnvelope(String username, int sum, int envelopeNum) {
        logger.info("用户{}准备发红包了", username);
        PayService payService = (PayService) applicationContext.getBean("payService");
        boolean queryAccount = payService.decAccount(username, sum);
        if (queryAccount) {
            logger.info("金额服务调用成功");
            // todo 模拟随机红包
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
