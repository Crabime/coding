package cn.crabime.dubbo.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoServiceMock implements DemoService {

    private final Logger logger = LoggerFactory.getLogger(DemoServiceMock.class);

    public String sayHello(String name) {
        try {
            Thread.sleep(270);
        } catch (InterruptedException e) {
            logger.error("线程出错：", e);
        }
        return "Mock Hello " + name;
    }
}
