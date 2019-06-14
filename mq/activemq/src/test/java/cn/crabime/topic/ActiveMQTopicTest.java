package cn.crabime.topic;

import cn.crabime.common.Constants;
import cn.crabime.db.OrderDao;
import cn.crabime.springsupport.Order;
import junit.framework.TestCase;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:beans.xml"))
public class ActiveMQTopicTest extends TestCase {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMQTopicTest.class);

    private int SENDERTHREADCOUNT = 10;

    @Autowired
    private MQDemoTopicSender topicSender;

    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    @Autowired
    private OrderDao orderDao;

    private Thread senderThread;

    @Test
    public void testTopic() {
        defaultMessageListenerContainer.setDestination(new ActiveMQTopic(Constants.TOPICSELFPROPOGATION));
        for (int i = 0; i < SENDERTHREADCOUNT; i++) {
            senderThread = new Thread(new SenderThread(topicSender, Constants.TOPICSELFPROPOGATION), "生产者线程" + i);
            senderThread.start();
        }

        try {
            // 防止junit主线程执行到这里就结束了
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendSerializableMessage() {
        List<Order> list = null;
        for (int i = 0; i < SENDERTHREADCOUNT; i++) {
            list = Utils.orderGenerator(10);
            senderThread = new Thread(new SenderThread(topicSender, Constants.TOPICOBJECT, (ArrayList) list), "生产者线程" + i);
            senderThread.start();
        }

        try {
            // 防止junit主线程执行到这里就结束了
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertThousandRecordIntoDb() {
        List<Order> orders = Utils.orderGenerator(10000);
        long startTime = System.currentTimeMillis();
        for (Order order : orders) {
            orderDao.insertOneOrder(order);
        }
        long endTime = System.currentTimeMillis();
        logger.warn("执行总时间：{}", endTime - startTime);
    }

}
