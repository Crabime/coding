package cn.crabime.topic;

import cn.crabime.common.Constants;
import cn.crabime.db.OrderDao;
import cn.crabime.springsupport.Order;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;
import java.util.List;

@Component
public class MQDemoTopicListener implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(MQDemoTopicListener.class);

	@Autowired
	private OrderDao orderDao;

	@Override
	public void onMessage(Message arg0) {
	    String topicToReceive = null;
	    logger.info("来导监听器中");
        try {
            if(arg0 instanceof TextMessage){
                TextMessage textMessage = (TextMessage) arg0;
                String receiveText = textMessage.getText();
                logger.info("接受到消息：" + receiveText);

            } else if (arg0 instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) arg0;
                Serializable serializable = objectMessage.getObject();
                topicToReceive = ((ActiveMQObjectMessage)objectMessage).getDestination().getPhysicalName();
                if (topicToReceive.equals(Constants.TOPICOBJECT)) {
                    List<Order> orderList = (List<Order>) serializable;
                    logger.info("接收到的订单为：{}", orderList);
                    for (Order order : orderList) {
                        // 数据库中插入订单
                        orderDao.insertOneOrder(order);
                    }
                }
            }
        } catch (JMSException e) {
            logger.error("接受消息异常");
        }
	}

}
