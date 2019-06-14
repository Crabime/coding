package cn.crabime.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.ServletContext;

public class MySecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        logger.info("开始创建Spring Security过滤器链");
    }

    @Override
    protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
        logger.info("Spring Security过滤器链创建完毕");
    }
}
