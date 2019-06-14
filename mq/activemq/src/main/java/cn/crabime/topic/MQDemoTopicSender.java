package cn.crabime.topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Session;
import java.io.Serializable;

@Component
public class MQDemoTopicSender {
    private static Logger logger = LoggerFactory.getLogger(MQDemoTopicSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendString(String msg, String topicToSend) {
        Destination destination = new ActiveMQTopic(topicToSend);

        jmsTemplate.send(destination, (Session session) -> {
            logger.info("消息{}已经广播出去了，请注意查收！", msg);
            return session.createTextMessage(msg);
        });
    }

    /**
     * 将对象信息发送到mq中，与当前应用进行解耦合
     *
     * @param object      待保存的对象
     * @param topicToSend 注册的topic
     */
    public void sendSerializableMessage(Serializable object, String topicToSend) {
        logger.info("消息{}已经广播出去了，请注意查收！", object);
        Destination destination = new ActiveMQTopic(topicToSend);
        jmsTemplate.convertAndSend(destination, object);
    }


}
