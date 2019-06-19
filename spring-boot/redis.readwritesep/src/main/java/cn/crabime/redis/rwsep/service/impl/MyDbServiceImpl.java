package cn.crabime.redis.rwsep.service.impl;

import cn.crabime.redis.rwsep.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyDbServiceImpl implements RedisService {

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
}
