package cn.crabime.normal;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;

/**
 * ͬ��JMS��������
 * @author Crabime
 *
 */
public class MQDemoSubscriber implements MessageListener, ExceptionListener {
	private String topic;
	private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	private Connection connection = null;
	private Session session = null;
	public MQDemoSubscriber(String topic) {
		this.topic = topic;
	}
	
	public void receive(){
		System.out.println("================");
		System.out.println("���Ѿ���ʼ������Ϣ��");
		try {
			connection = factory.createConnection();
			connection.start();
			connection.setExceptionListener(this);
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(topic);
			MessageConsumer receiver = session.createConsumer(destination);
			//�������1�룬���׳��쳣��������ͬ�����գ�������첽�ģ���ôʹ��setMessageListener��������ɣ�
//			receiver.receive(1000);
			receiver.setMessageListener(this);
		} catch (JMSException e){
			e.printStackTrace();
		} 
	}
	@Override
	public void onMessage(Message receiveMessage) {
		System.out.println("==================");
		System.out.println("�յ���Ϣ��!");
		if(receiveMessage instanceof TextMessage){
			try {
				TextMessage messages = (TextMessage) receiveMessage;
				String text = messages.getText();
				System.out.println(TimeUtils.getCurrentTime() + "Subscriber:\r\n\tI am :" + Thread.currentThread().getName() + " message inside my body is :" + text);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onException(JMSException arg0) {
		System.out.println("Message connection Error!");
	}
	
	public static void main(String[] args) {
		MQDemoSubscriber subscriber = new MQDemoSubscriber(Constants.topic);
		subscriber.receive();
	}
}
