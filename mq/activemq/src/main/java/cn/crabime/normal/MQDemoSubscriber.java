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
 * 同步JMS请求订阅者
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
		System.out.println("我已经开始接受消息了");
		try {
			connection = factory.createConnection();
			connection.start();
			connection.setExceptionListener(this);
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(topic);
			MessageConsumer receiver = session.createConsumer(destination);
			//如果超过1秒，将抛出异常（这里是同步接收，如果是异步的，那么使用setMessageListener方法来完成）
//			receiver.receive(1000);
			receiver.setMessageListener(this);
		} catch (JMSException e){
			e.printStackTrace();
		} 
	}
	@Override
	public void onMessage(Message receiveMessage) {
		System.out.println("==================");
		System.out.println("收到消息了!");
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
