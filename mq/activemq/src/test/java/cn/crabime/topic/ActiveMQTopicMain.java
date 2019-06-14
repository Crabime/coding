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
 * ��������Ҫͨ�����߳�ģ���ֵ�û�������junit main�̲߳���ȵ����߳̽���������ͨ��Thread.sleep(Integer.MAX_INT)���������̣߳�
 * ���Ա��뽫���Էŵ�main������ִ�У���֤�������߳�ִ�н��������߳��ٽ���
 */
public class ActiveMQTopicMain {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMQTopicMain.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        MQDemoTopicSender topicSender = context.getBean(MQDemoTopicSender.class);
        List<Order> list = orderGenerator(10000);
        long startTime = System.currentTimeMillis();
        Thread senderThread = new Thread(new SenderThread(topicSender, Constants.TOPICOBJECT, (ArrayList) list), "�������߳�");
        senderThread.start();
        long endTime = System.currentTimeMillis();
        logger.warn("�ܺ�ʱ:{}", endTime - startTime);
    }

}
