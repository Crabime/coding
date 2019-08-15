package cn.crabime.dubbo.client;

import cn.crabime.dubbo.server.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class Consumer {

    private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final static int THREAD_NUM = 15;

    private static CopyOnWriteArrayList<String> errorThreadList = new CopyOnWriteArrayList<>();

    private static CountDownLatch latch = new CountDownLatch(THREAD_NUM);

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理

        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    String result = demoService.sayHello("world");
                    logger.info("结果为：" + result);
                } catch (Exception e) {
                    String threadName = Thread.currentThread().getName();
                    errorThreadList.add(threadName);
                    logger.error("线程报错", e);
                } finally {
                    latch.countDown();
                }
            }, "线程" + i);
            threads[i].start();
        }

        latch.await();
        logger.info("连接失败的线程数有：" + errorThreadList.size());
    }
}
