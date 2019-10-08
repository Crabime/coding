package cn.crabime.normal;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Crabime
 *
 */
public class MQDemoSubscriber implements MessageListener, ExceptionListener {
	private String topic;
	private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.103.3.149:61616");
	private Connection connection = null;
	private Session session = null;
	public MQDemoSubscriber(String topic) {
		this.topic = topic;
	}
	
	public void receive(){
		System.out.println("================");
		try {
			connection = factory.createConnection();

			connection.start();
			connection.setExceptionListener(this);
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(topic);
			MessageConsumer receiver = session.createConsumer(destination);
//			receiver.receive(1000);
			receiver.setMessageListener(this);
		} catch (JMSException e){
			e.printStackTrace();
		} 
	}
	@Override
	public void onMessage(Message receiveMessage) {
		System.out.println("==================");
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
