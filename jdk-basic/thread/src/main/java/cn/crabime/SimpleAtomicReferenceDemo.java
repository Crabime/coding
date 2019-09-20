package cn.crabime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleAtomicReferenceDemo {

    private final static Logger logger = LoggerFactory.getLogger(SimpleAtomicReferenceDemo.class);

    private AtomicReference<Integer> reference = new AtomicReference<>(1000);

    static class Task implements Runnable {

        AtomicReference<Integer> obj;

        public Task(AtomicReference<Integer> obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            int i = 0;
            // 自旋，操作成功截至
            for (;;) {
                Integer val = obj.get();
                if (obj.compareAndSet(val, val + 1)) {
                    logger.info("成功");
                    break;
                }
                logger.info("自旋次数{}", ++i);
            }
        }
    }

    public void incToThousand(int len) {
        List<Thread> threads = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            Thread thread = new Thread(new Task(reference), "线程" + i);
            threads.add(thread);
            thread.start();
        }

        for (Thread t : threads) {

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(reference.get());
    }

    public static void main(String[] args) {
        SimpleAtomicReferenceDemo atomicReferenceDemo = new SimpleAtomicReferenceDemo();
        atomicReferenceDemo.incToThousand(1000);
    }
}
