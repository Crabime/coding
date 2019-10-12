package cn.crabime.spring.aop.practice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAspect {

    @Pointcut("execution(* cn.crabime.spring.aop.practice.UserDao.addUser(..))")
    private void addUserPointCut() {

    }

    @Before("execution(* cn.crabime.spring.aop.practice.UserDao.addUser(..))")
    public void before() {
        System.out.println("前置处理");
    }

    @AfterReturning(value = "execution(* cn.crabime.spring.aop.practice.UserDao.addUser(..))", returning = "val")
    public void afterReturningProxy(Object val) {
        System.out.println("后置处理，返回值为：" + val);
    }

    @Around("execution(* cn.crabime.spring.aop.practice.UserDao.addUser(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕执行前");
        Object result = joinPoint.proceed();
        System.out.println("环绕执行后");
        return result;
    }

    // 无论如何都会执行的
    @After(value = "addUserPointCut()")
    public void after() {
        System.out.println("最终通知...");
    }

}
