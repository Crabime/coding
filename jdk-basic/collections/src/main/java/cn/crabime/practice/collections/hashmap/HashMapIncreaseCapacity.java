package cn.crabime.practice.collections.hashmap;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 理解HashMap中扩容机制原理
 */
public class HashMapIncreaseCapacity {

    private static int threshold;

    public static void main(String[] args) {

        HashMap<String, Integer> map = new HashMap<>(3);
        getOrCreateThreshold(map);

        put("0", 0, map);
        put("张三", 25, map);

        put("李四", 20, map);
        put("王五", 21, map);
        put("里流", 19, map);
        put("世界", 23, map);
        put("crabime", 25, map);
        put("asteroid", 27, map);


    }

    private static void getOrCreateThreshold(Map<String, Integer> map) {
        threshold = (int) getDeclaredFieldVal("threshold", map);
        System.out.println("当前map扩容阈值为:" + threshold);
    }

    private static Object getDeclaredFieldVal(String fieldName, Map instance) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void invoke(String method, Map instance) {
        try {
            Method m = instance.getClass().getDeclaredMethod(method);
            m.setAccessible(true);
            m.invoke(instance);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void put(String str, Integer val, Map<String, Integer> map) {
        int h;
        int v = (h = str.hashCode()) ^ (h >>> 16);
        Object nodeArray = getDeclaredFieldVal("table", map);
        map.put(str, val);
        if (map.size() > threshold) {
            System.out.println("map进行了一次扩容");
            getOrCreateThreshold(map);
        }
        if (nodeArray != null) {
            System.out.println("key=" + str + ",val=" + val + ",hashcode=" + v + ",slot=" +
                    (v & (Array.getLength(nodeArray) - 1)) + ",tableSize=" + Array.getLength(nodeArray));
        }
    }

}
