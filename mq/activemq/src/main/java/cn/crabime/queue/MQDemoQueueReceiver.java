package cn.crabime.queue;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MQDemoQueueReceiver implements MessageListener {
	private ActiveMQConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession queueSession;
	
	public MQDemoQueueReceiver() {
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
	
	public void receiveString(){
		try {
			Queue queue = queueSession.createQueue(Constants.QUEUESELFPROPOGATION);
			QueueReceiver queueReceiver = queueSession.createReceiver(queue);
			queueReceiver.setMessageListener(this);
			
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

	@Override
	public void onMessage(Message arg0) {
		if(arg0 instanceof TextMessage){
			TextMessage message = (TextMessage) arg0;
			try {
				System.out.println(TimeUtils.getCurrentTime() + "Receiver : " + message.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MQDemoQueueReceiver receiver = new MQDemoQueueReceiver();
		receiver.receiveString();
	}
}
