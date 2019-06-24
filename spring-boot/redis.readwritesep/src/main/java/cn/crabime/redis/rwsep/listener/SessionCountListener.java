package cn.crabime.redis.rwsep.listener;

import cn.crabime.redis.rwsep.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 统计在线人数
 * WebListener注解配合ServletComponentScan注解，能自动注册servlet listener
 */
@Component
public class SessionCountListener implements HttpSessionListener {

    private final static Logger logger = LoggerFactory.getLogger(SessionCountListener.class);

    @Autowired
    private RedisService redisService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        redisService.addUserSession(httpSessionEvent.getSession().getId());
        logger.info("创建session {}", httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("session[{}]被销毁", httpSessionEvent.getSession().getId());
    }
}
