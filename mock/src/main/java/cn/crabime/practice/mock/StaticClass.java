package cn.crabime.practice.mock;

import java.util.UUID;

/**
 *  包含静态方法类
 */
public class StaticClass {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 该方法未被调用
     */
    public static void neverUsed() {
        System.out.println("我从未被调用过");
    }
}
