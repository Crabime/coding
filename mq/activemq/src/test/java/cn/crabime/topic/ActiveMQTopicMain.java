package cn.crabime.topic;

import cn.crabime.common.Constants;
import cn.crabime.springsupport.Order;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static cn.crabime.topic.Utils.orderGenerator;

/**
 * 由于这里要通过多线程模拟峰值用户操做，junit main线程不会等到自线程结束，必须通过Thread.sleep(Integer.MAX_INT)来阻塞主线程，
 * 所以必须将测试放到main函数中执行，保证所有子线程执行结束后，主线程再结束
 */
public class ActiveMQTopicMain {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMQTopicMain.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        MQDemoTopicSender topicSender = context.getBean(MQDemoTopicSender.class);
        List<Order> list = orderGenerator(10000);
        long startTime = System.currentTimeMillis();
        Thread senderThread = new Thread(new SenderThread(topicSender, Constants.TOPICOBJECT, (ArrayList) list), "生产者线程");
        senderThread.start();
        long endTime = System.currentTimeMillis();
        logger.warn("总耗时:{}", endTime - startTime);
    }

}
