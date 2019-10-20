package cn.crabime.mq.practice.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:rocketmq.properties")
public class RocketMQConfig {

    private final static Logger logger = LoggerFactory.getLogger(RocketMQConfig.class);

    @Value("${mq.namesrv}")
    private String namesrvAddr;

    @Bean("MQProducer")
    public DefaultMQProducer createProducer() throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("producer");
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.start();
        return defaultMQProducer;
    }

    @Bean("MQConsumer")
    public DefaultMQPushConsumer createPullConsumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("consumer");
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.subscribe("orders", "*");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext context) {
                MessageExt ext = msg.get(0);
                logger.info("消费者接收到消息：{}", new String(ext.getBody()));

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultMQPushConsumer.start();
        return defaultMQPushConsumer;
    }
}
