package cn.crabime.practice.xml.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class InsertInterceptor implements Interceptor {

    private final static Logger logger = Logger.getLogger(InsertInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("进入拦截器了");

        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
