package cn.crabime.mq.practice.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private DefaultMQProducer producer;

    @RequestMapping(value = "/con", method = RequestMethod.GET)
    public void consume(@RequestParam("mes") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        logger.info("生产消息：{}", message);
        Message mg = new Message("orders", message.getBytes());
        producer.send(mg);
    }
}
