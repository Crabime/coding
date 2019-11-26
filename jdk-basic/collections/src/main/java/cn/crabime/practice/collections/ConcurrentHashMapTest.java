package cn.crabime.practice.collections;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 20; i++) {
            concurrentHashMap.put(i, i * 2);
        }
        System.out.println(concurrentHashMap.get(12));
    }
}
