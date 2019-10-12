package cn.crabime.spring.aop.practice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(AopConfig.class);
        UserDao userDao = atx.getBean(UserDao.class);
        userDao.addUser();
    }
}
