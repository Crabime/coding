package cn.crabime.redis.rwsep;

import cn.crabime.redis.rwsep.config.MybatisDaoMarker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.crabime.redis.rwsep.service"}, annotationClass = MybatisDaoMarker.class)
public class RWSepMain {

    public static void main(String[] args) {

        SpringApplication.run(RWSepMain.class, args);
    }
}
