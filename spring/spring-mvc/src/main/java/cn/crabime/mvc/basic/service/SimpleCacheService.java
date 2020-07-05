package cn.crabime.mvc.basic.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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

    public String getProperName(String name) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        List<Integer> indexList = new ArrayList<>();
        boolean exists = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            String index = "";
            if (key.startsWith(name)) {
                index = key.substring(key.indexOf(name) + name.length());
                exists = true;
            }
            if (StringUtils.isEmpty(index)) {
                index = "0";
            }
            int i = Integer.parseInt(index);
            indexList.add(i);
        }
        if (!exists) {
            return name;
        }
        indexList.sort(Integer::compareTo);
        int maxIndex = indexList.get(indexList.size() - 1) + 1;
        name = name + maxIndex;
        return name;
    }
}
