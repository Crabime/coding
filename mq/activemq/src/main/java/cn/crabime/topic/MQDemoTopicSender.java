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
            logger.info("��Ϣ{}�Ѿ��㲥��ȥ�ˣ���ע����գ�", msg);
            return session.createTextMessage(msg);
        });
    }

    /**
     * ��������Ϣ���͵�mq�У��뵱ǰӦ�ý��н����
     *
     * @param object      ������Ķ���
     * @param topicToSend ע���topic
     */
    public void sendSerializableMessage(Serializable object, String topicToSend) {
        logger.info("��Ϣ{}�Ѿ��㲥��ȥ�ˣ���ע����գ�", object);
        Destination destination = new ActiveMQTopic(topicToSend);
        jmsTemplate.convertAndSend(destination, object);
    }


}
