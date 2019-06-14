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
 * JMS��������������
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
		System.out.println("���Ѿ���ʼ����Ϣ�ˣ�ע����հ���");
		Connection connection = null;
		Session session = null;
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(topic);

			//������Ϣ������
			MessageProducer publisher = session.createProducer(destination);
			//���͵���Ϣ��Ҫ�洢���ڴ���
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
	
	//������Ϊ��Session��Connection���ر��ˣ����Կ���ͨ���������main�������ý����߳���������Ϣ
	public static void main(String[] args) {
		MQDemoProducer publisher = new MQDemoProducer("American The big bang theory", Constants.topic);
		MQDemoProducer publisher1 = new MQDemoProducer("China Story of a journey to west", Constants.topic);
		publisher.send();
		publisher1.send();
	}
}
