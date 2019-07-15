package cn.crabime.set;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSetTest {

    private final static int SIZE = 100;

    public static void main(String[] args) throws InterruptedException {
        Set<String> set = new HashSet<>();

//        Thread.sleep(100);
//        while (true) {
//            if (cyclicBarrier.getNumberWaiting() == 0) {
//                System.out.println("reset cyclicBarrier");
//                cyclicBarrier.reset();
//                break;
//            }
//        }

        Map<String, Boolean> map = new ConcurrentHashMap<>(100);
        Set<String> concurrentSet = Collections.newSetFromMap(map);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(SIZE, new Runnable() {
            @Override
            public void run() {
                System.out.println("set集合为：" + concurrentSet.size());
            }
        });
        Thread[] threads = new Thread[SIZE];
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < SIZE; i++) {
            threads[i] = new Thread(() -> {
                concurrentSet.add(String.valueOf(atomicInteger.getAndIncrement()));
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }

    }
}
