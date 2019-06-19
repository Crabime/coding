package cn.crabime.redis.rwsep.service.impl;

import cn.crabime.redis.rwsep.service.MyDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyDbServiceImpl implements MyDbService {

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
