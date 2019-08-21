package cn.crabime.redis.rwsep;

import cn.crabime.redis.rwsep.config.MybatisDaoMarker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringApplication作用：
 *  1、创建ApplicationContext
 *  2、注册CommandLinePropertySource，它的作用是将命令行参数转化为spring的properties属性
 *  3、刷新上下文，加载所有单例
 *  4、创发CommandLineRunner实例
 *
 * 很多情况下，我们直接调用SpringApplication#run(Class, String[]就可以启动应用了。
 *
 *
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.crabime.redis.rwsep.service"}, annotationClass = MybatisDaoMarker.class)
public class RWSepMain extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(RWSepMain.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RWSepMain.class);
    }
}
