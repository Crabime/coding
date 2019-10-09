package cn.crabime.normal;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import javax.jms.*;

/**
 * @author Crabime
 *
 */
public class MQDemoProducer {
	private String msg;
	private String topic;
	public MQDemoProducer(String msg, String topic) {
		this.msg = msg;
		this.topic = topic;
	}
	
	public void send(){
		System.out.println("================");
		TopicConnection connection = null;
		ActiveMQSession session = null;
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.103.3.149:61616");
		try {
			connection = factory.createTopicConnection();
			connection.start();
			// session自动启动并创建事务
			session = (ActiveMQSession) connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

			Destination destination = session.createQueue(topic);

			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage message = session.createTextMessage(msg);
			producer.send(destination, message);
			System.out.println(TimeUtils.getCurrentTime() + "Publisher:\r\n\tI am :" + 
					Thread.currentThread().getName() + " message inside my body is :" + msg);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally{
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MQDemoProducer publisher = new MQDemoProducer("American The big bang theory", Constants.topic);
		MQDemoProducer publisher1 = new MQDemoProducer("China Story of a journey to west", Constants.topic);
		publisher.send();
		publisher1.send();
	}
}
