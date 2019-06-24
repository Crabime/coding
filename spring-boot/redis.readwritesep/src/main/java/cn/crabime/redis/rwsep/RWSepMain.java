package cn.crabime.redis.rwsep;

import cn.crabime.redis.rwsep.config.MybatisDaoMarker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

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
