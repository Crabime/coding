package cn.crabime.redis.rwsep;

import cn.crabime.redis.rwsep.service.MyDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyDbServiceImpl implements MyDbService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insert(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
