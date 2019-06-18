package cn.crabime.redis.rwsep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

@Configuration
public class RedisCacheConfig {

    @Value("${spring.redis.sentinel.nodes}")
    private String redisSentinelNodes;

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        // TODO: 6/19/19 未完待续：参考地址https://blog.csdn.net/u011521890/article/details/78088799 
        return configuration;
    }
}
