package cn.crabime.mq.practice.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private DefaultMQProducer producer;

    @RequestMapping(value = "/con", method = RequestMethod.GET)
    @ResponseBody
    public String consume(@RequestParam("mes") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        logger.info("生产消息：{}", message);
        Message mg = new Message("orders", message.getBytes());
        // rocketmq发送同步消息，默认timeout时间为3s，发送方式为CommunicationMode.ASYNC(同步方式)，回调为空
        producer.send(mg);
        return "Request Success";
    }

    @RequestMapping(value = "/produce", method = RequestMethod.GET)
    @ResponseBody
    public String produceMessageConcurrently(@RequestParam("mes") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // TODO: 2019/10/21 增加消息事务管理，优化jmeter参数，保证后续cpu能达到100%
        String topic = "orders";

        List<Message> messageList = new ArrayList<>(10);
        String[] allMessageArray = message.split(":");
        for (String res : allMessageArray) {
            messageList.add(new Message(topic, res.getBytes()));
        }

        MessageQueue messageQueue = new MessageQueue(topic, "broker-a", 1);
        producer.send(messageList, messageQueue, 2000);
        return "Request Success";
    }
}
