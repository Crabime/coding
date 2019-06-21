package cn.crabime.redis.rwsep.config;

import cn.crabime.redis.rwsep.listener.SessionCountListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisCacheConfig {

    @Value("${spring.redis.sentinel.nodes}")
    private String redisSentinelNodes;

    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();

        String[] hosts = redisSentinelNodes.split(",");
        for (String host : hosts) {
            String[] h = host.split(":");
            configuration.addSentinel(new RedisNode(h[0], Integer.parseInt(h[1])));
        }
        // 设置sentinel master节点
        configuration.master(sentinelMaster);
        return configuration;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 将Listener注册到Spring容器中
     */
    @Bean
    public ServletListenerRegistrationBean sessionCountListener() {
        ServletListenerRegistrationBean<SessionCountListener> registrationBean =
                new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new SessionCountListener());
        return registrationBean;
    }
}
