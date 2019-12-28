package cn.crabime.mvc.basic.service;

import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将IndexController中结果存放在servlet上下文里
 */
@Service
public class SimpleCacheService {

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(10);

    public boolean put(String key, Object val) {
        return map.put(key, val) != null;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Enumeration keys() {
        return map.keys();
    }
}
