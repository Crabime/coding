package cn.crabime.queue;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Crabime
 *
 */
public class MQDemoQueueSender {
	private ActiveMQConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession queueSession;
	
	public MQDemoQueueSender() {
		System.out.println("==============");
		factory = new ActiveMQConnectionFactory(Constants.broker);
		try {
			connection = factory.createQueueConnection();
			connection.start();
			queueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendString(String message){
		try {
			Queue queue = queueSession.createQueue(Constants.QUEUESELFPROPOGATION);
			QueueSender queueSender = queueSession.createSender(queue);
			queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage textMessage = queueSession.createTextMessage(message);
			queueSender.send(textMessage);
			System.out.println(TimeUtils.getCurrentTime() + "Publisher : " + message);
			close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void close() throws JMSException{
		if(connection != null){
			connection.close();
		}
		if(queueSession != null){
			queueSession.close();
		}
	}
	
	public static void main(String[] args) {
		MQDemoQueueSender topicSender = new MQDemoQueueSender();
		topicSender.sendString("hello guy");
	}
}
