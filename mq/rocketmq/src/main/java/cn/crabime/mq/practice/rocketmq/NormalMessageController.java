package cn.crabime.mq.practice.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
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

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.crabime.mq.practice.rocketmq.core.Constant.TOPIC_NORMAL_ORDER;

@Controller
@RequestMapping("/message")
public class NormalMessageController {

    private final static Logger logger = LoggerFactory.getLogger(NormalMessageController.class);

    @Autowired
    @Qualifier("MQProducer")
    private DefaultMQProducer producer;

    private final Object lock = new Object();

    private AtomicInteger id = new AtomicInteger(0);

    private int queueSize = 0;

    @RequestMapping(value = "/con", method = RequestMethod.GET)
    @ResponseBody
    public String consume(@RequestParam("mes") String message,
                          @RequestParam(name = "topic", defaultValue = TOPIC_NORMAL_ORDER) String topic) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        logger.info("生产消息：{}", message);
        Message mg = new Message(topic, message.getBytes());
        // rocketmq发送同步消息，默认timeout时间为3s，发送方式为CommunicationMode.ASYNC(同步方式)，回调为空
        producer.send(mg);
        return "Request send normal message Success";
    }

    @RequestMapping(value = "/produce", method = RequestMethod.GET)
    @ResponseBody
    public String produceMessageConcurrently(@RequestParam("mes") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // TODO: 10/22/19 增加传过来消息ID设置
        Message mg = new Message(TOPIC_NORMAL_ORDER, "mes", message.getBytes(Charset.defaultCharset()));

        producer.send(mg, new MessageQueueSelector() {

            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer index = (Integer) arg;
                int messageQueueSize = mqs.size();
                synchronized (lock) {
                    if (messageQueueSize != queueSize) {
                        logger.info("当前队列大小为{}", messageQueueSize);
                        queueSize++;
                    }
                }
                int queueIndex = index % messageQueueSize;
                return mqs.get(queueIndex);
            }
        }, id.addAndGet(1));
        return "Request Success";
    }
}
