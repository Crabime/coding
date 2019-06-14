package cn.crabime;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import cn.crabime.springsupport.Person;
import cn.crabime.utils.TimeUtils;

public class SpringContextTest {
	private ApplicationContext ctx;
	@Before
	public void config(){
		ctx = new FileSystemXmlApplicationContext("classpath:beans.xml");
	}

	@Test
	public void testSpringConfiguration(){
		Person person = ctx.getBean("person", Person.class);
		person.say();
	}
	
	@Test
	public void testSpringJmsSupport(){
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("message", TimeUtils.getCurrentTime() + " : Message from sender");
				return mapMessage;
			}
		});
		
		Map<String, Object> mapMessage = (Map<String, Object>) jmsTemplate.receiveAndConvert();
		String message = (String) mapMessage.get("message");
		System.out.println("收到消息了:\r\n\t" + message);
	}
}
