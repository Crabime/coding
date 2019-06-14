package cn.crabime.normal;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;

/**
 * JMS服务中心生产者
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
		System.out.println("我已经开始发消息了，注意查收啊！");
		Connection connection = null;
		Session session = null;
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(topic);

			//构建消息发送者
			MessageProducer publisher = session.createProducer(destination);
			//发送的消息需要存储到内存中
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage message = session.createTextMessage(msg);
			publisher.send(destination, message);
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
	
	//这里因为把Session、Connection都关闭了，所以可以通过多次运行main函数，让接受者持续接收消息
	public static void main(String[] args) {
		MQDemoProducer publisher = new MQDemoProducer("American The big bang theory", Constants.topic);
		MQDemoProducer publisher1 = new MQDemoProducer("China Story of a journey to west", Constants.topic);
		publisher.send();
		publisher1.send();
	}
}
