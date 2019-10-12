package cn.crabime.practice.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * 多线程下HashMap存取模拟(失败)
 *
 * @author crabime
 * create at 2019/10/11
 */
public class HashMapInConcurrent {

    private static Map<String, Integer> map = new HashMap<String, Integer>(2, 0.75f);

    static class Worker1 extends Thread {

        public Worker1(String name) {
            super(name);
        }

        @Override
        public void run() {
            map.put("w1", 1);
            System.out.println(map);
        }
    }

    static class Worker2 extends Thread {

        public Worker2(String name) {
            super(name);
        }

        @Override
        public void run() {
            map.put("w2", 2);
            System.out.println(map);
        }
    }


    public static void main(String[] args) {
        map.put("w", 3);
        Worker1 t1 = new Worker1("worker1");
        Worker2 t2 = new Worker2("worker2");
        t1.start();
        t2.start();
    }
}
