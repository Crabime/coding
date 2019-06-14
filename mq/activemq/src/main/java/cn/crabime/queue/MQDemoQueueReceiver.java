package cn.crabime.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import cn.crabime.utils.TimeUtils;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.crabime.common.Constants;

public class MQDemoQueueReceiver implements MessageListener {
	private ActiveMQConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession queueSession;
	
	public MQDemoQueueReceiver() {
		System.out.println("==============");
		System.out.println("�����߳�:" + Thread.currentThread().getName() + "�ҿ�ʼ������Ϣ��");
		factory = new ActiveMQConnectionFactory(Constants.broker);
		try {
			//���� һ����Ե�ʽ����
			connection = factory.createQueueConnection();
			connection.start();
			queueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���յ�queue�е���Ϣ
	 */
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
