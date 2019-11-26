package cn.crabime.mq.practice.rocketmq;

import cn.crabime.mq.practice.rocketmq.core.mq.TransactionExecutorImpl;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

import static cn.crabime.mq.practice.rocketmq.core.Constant.*;

@Controller
@RequestMapping(value = "/tran")
public class TransactionMessageController {

    private final static Logger logger = LoggerFactory.getLogger(TransactionMessageController.class);

    private Random random = new Random();

    @Autowired
    @Qualifier("transactionProducer")
    private DefaultMQProducer producer;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public String consume(@RequestParam("mes") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        logger.info("生产消息：{}", message);

        // 接收事务监听器
        TransactionExecutorImpl executor = new TransactionExecutorImpl();

        String tag = TAG_TRANSACTION_PREFIX + random.nextInt(3);

        logger.info("tag={}", tag);

        Message m = new Message(TOPIC_NORMAL_TRANSACTION_MESSAGE, tag, message.getBytes());

        TransactionSendResult sendResult = producer.sendMessageInTransaction(m, executor, TRANSACTION_MESSAGE_ARGUMENT);

        logger.info("send result {}", sendResult);
        return "Request Success";
    }

}
