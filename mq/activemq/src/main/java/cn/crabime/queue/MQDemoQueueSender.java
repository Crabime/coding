package cn.crabime.queue;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.crabime.common.Constants;
import cn.crabime.utils.TimeUtils;

/**
 * Queue������ʽ����p2p��Ϣ
 * @author Crabime
 *
 */
public class MQDemoQueueSender {
	private ActiveMQConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession queueSession;
	
	public MQDemoQueueSender() {
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
	 * ����String������Ϣ
	 * @param message �ַ�����Ϣ
	 */
	public void sendString(String message){
		try {
			Queue queue = queueSession.createQueue(Constants.QUEUESELFPROPOGATION);
			//������Ϣ������
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
