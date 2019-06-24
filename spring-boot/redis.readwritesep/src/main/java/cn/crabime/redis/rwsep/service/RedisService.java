package cn.crabime.redis.rwsep.service;

import java.util.Date;

public interface RedisService {

    interface SessionGenerator{
        String generateBaseKey();

        String getKey(Date date);
    }

    void insert(String key, Object value);

    Object get(String key);

    Long addUserSession(String sessionId);

    Long countUserSession();
}
