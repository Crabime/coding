package cn.crabime.redis.rwsep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.crabime.redis.rwsep.service")
public class RWSepMain {

    public static void main(String[] args) {

        SpringApplication.run(RWSepMain.class, args);
    }
}
