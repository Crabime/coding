package cn.crabime.redis.rwsep.service;

public interface MyDbService {

    void insert(String key, Object value);

    Object get(String key);
}
