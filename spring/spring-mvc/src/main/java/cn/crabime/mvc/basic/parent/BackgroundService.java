package cn.crabime.mvc.basic.parent;

import cn.crabime.mvc.basic.service.SimpleCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class BackgroundService implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(BackgroundService.class);

    @Autowired
    private SimpleCacheService simpleCacheService;

    @Override
    public void afterPropertiesSet() {
        logger.info("{}开始初始化了...", this.getClass().getName());
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Enumeration keys = simpleCacheService.keys();
                Object next;
                while (keys.hasMoreElements()) {
                    next = keys.nextElement();
                    logger.info("next={}", next);
                }
            }
        }, 10, 5, TimeUnit.SECONDS);
    }
}
