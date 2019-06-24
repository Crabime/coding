package cn.crabime.redis.rwsep.service.impl;

import cn.crabime.redis.rwsep.config.MyConstant;
import cn.crabime.redis.rwsep.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RedisServiceImpl implements RedisService {

    private class SessionGeneratorImpl implements SessionGenerator {
        @Override
        public String generateBaseKey() {
            return MyConstant.IPCOUNT + new SimpleDateFormat("yyyyMMdd").format(new Date());
        }

        @Override
        public String getKey(Date date) {
            return MyConstant.IPCOUNT + new SimpleDateFormat("yyyyMMdd").format(date);
        }
    }

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void insert(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long addUserSession(String sessionId) {
        return redisTemplate.execute((RedisConnection connection) -> connection.pfAdd(new SessionGeneratorImpl().generateBaseKey().getBytes(), sessionId.getBytes()));
    }

    @Override
    public Long countUserSession() {
        return redisTemplate.execute((RedisConnection connection) -> connection.pfCount(new SessionGeneratorImpl().getKey(new Date()).getBytes()));
    }
}
