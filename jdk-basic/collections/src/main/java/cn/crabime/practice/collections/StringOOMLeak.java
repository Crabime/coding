package cn.crabime.practice.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟永久代撑爆
 * vm args: -Xmx20m -Xms20m -XX:PermSize=10m -XX:MaxPermSize=10m
 */
public class StringOOMLeak {

    static String str = "hello";

    static void appendStr() {
        List<String> list = new ArrayList<>();
        String as = "world";
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            str = str + as;
            list.add(str.intern());
        }
    }

    public static void main(String[] args) {
        appendStr();
    }
}
