package cn.crabime.practice.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapTest {

    private ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    @Test
    public void testCompute() {
        concurrentHashMap.put("a", 123);
        BiFunction<String, Integer, Integer> function = (s, val) -> {
            if (val != null) {
                return s.length() > 3 ? Math.max(val, 8) : Math.min(val, 2);
            }
            return null;
        };
        BiFunction<String, Integer, Integer> fc = (s, val) -> val != null ? (s.length() > 3 ? Math.max(val, 8) : Math.min(val, 2)) : -1;
        concurrentHashMap.compute("a", fc);
        concurrentHashMap.compute("ccd", fc);
        Assert.assertEquals(2, concurrentHashMap.get("a").intValue());
        Assert.assertEquals(-1, concurrentHashMap.get("ccd").intValue());

    }

}
