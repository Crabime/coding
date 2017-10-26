package cn.crabime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by crabime on 11/23/16.
 * 让bean获取spring容器
 */
public class ApplicationContextAwareTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext ctx){
        this.applicationContext = ctx;
    }

    //查看Bean初始化时期
    public void teacherCheck(){
        Teacher teacher = applicationContext.getBean("teacher", Teacher.class);
        teacher.check();
    }
}
