package cn.crabime.dubbo.server.service.impl;

import cn.crabime.dubbo.server.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crabime on 6/6/17.
 */
public class DemoServiceImpl implements DemoService {

    private final static Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    public String sayHello(String name) {
        try {
            Thread.sleep(270);
        } catch (InterruptedException e) {
            logger.error("线程出错：", e);
        }
        return "Hello " + name;
    }

}
