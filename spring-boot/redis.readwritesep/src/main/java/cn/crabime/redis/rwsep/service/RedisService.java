package cn.crabime.redis.rwsep.service;

public interface RedisService {

    void insert(String key, Object value);

    Object get(String key);
}