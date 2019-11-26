package cn.crabime.mq.practice.rocketmq.core.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static cn.crabime.mq.practice.rocketmq.core.Constant.*;

@Configuration
@PropertySource("classpath:rocketmq.properties")
public class TransactionMessageMQConfig {

    private final static Logger logger = LoggerFactory.getLogger(TransactionMessageMQConfig.class);

    @Value("${mq.namesrv}")
    private String namesrvAddr;

    @Bean(name = "transactionProducer")
    public TransactionMQProducer createTransactionProducer() throws MQClientException, InterruptedException {
        // 定义一个事务生产者
        TransactionMQProducer producer = new TransactionMQProducer(TRANSACTION_PRODUCER);
        producer.setNamesrvAddr(namesrvAddr);

        // 还可以设置TransactionMQProducer对象用于事务回查线程池最小／最大线程数量，
        // 详细常见DefaultMQProducerImpl#initTransactionEnv
        producer.setCheckThreadPoolMaxSize(20);

        producer.setTransactionCheckListener(new TransactionCheckListener() {
            @Override
            public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
                logger.info("state--{}", new String(msg.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        producer.start();

        return producer;
    }

    @Bean(name = "transactionConsumer")
    public DefaultMQPushConsumer createTransactionPullConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TRANSACTION_CONSUMER);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(TOPIC_NORMAL_TRANSACTION_MESSAGE, "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);

                for (MessageExt messageExt : msgs) {
                    logger.info("接收到消息:{}", new String(messageExt.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();

        return consumer;
    }
}
