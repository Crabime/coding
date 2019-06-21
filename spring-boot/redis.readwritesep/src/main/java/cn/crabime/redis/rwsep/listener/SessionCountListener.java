package cn.crabime.redis.rwsep.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 统计在线人数
 * WebListener注解配合ServletComponentScan注解，能自动注册servlet listener
 */
public class SessionCountListener implements HttpSessionListener {

    private final static Logger logger = LoggerFactory.getLogger(SessionCountListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("创建session {}", httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("session[{}]被销毁", httpSessionEvent.getSession().getId());
    }
}
