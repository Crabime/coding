package cn.crabime.spring.aop.practice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "cn.crabime.spring.aop.practice")
@EnableAspectJAutoProxy
public class AopConfig {
}
