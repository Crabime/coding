package cn.crabime.dubbo.server.service.impl;

import cn.crabime.dubbo.server.service.DemoService;
import com.alibaba.dubbo.config.ProtocolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Iterator;

/**
 * Created by crabime on 6/6/17.
 */
// TODO: 2019/10/16 增加Producer调第三方服务，达到服务治理目的
public class DemoServiceImpl implements DemoService, InitializingBean, BeanFactoryAware {

    private final static Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private DefaultListableBeanFactory beanFactory;

    public String sayHello(String name) {
        try {
            Thread.sleep(270);
        } catch (InterruptedException e) {
            logger.error("线程出错：", e);
        }
        ProtocolConfig protocolConfig = this.beanFactory.getBean(ProtocolConfig.class);
        logger.info("调用来到我这里了...，host={}, port={}", protocolConfig.getHost(), protocolConfig.getPort());
        return "Hello " + name;
    }

    private void iterateBeanNames() {
        Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()) {
            String beanName = beanNamesIterator.next();
            try {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                logger.info("beanName={}, beanDefinition={}", beanName, beanDefinition);
            } catch (NoSuchBeanDefinitionException e) {
                logger.info("无法创建bean={}", beanName);
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        iterateBeanNames();
    }
}
