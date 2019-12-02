package cn.crabime.mq.practice.rocketmq.core.mq;

import cn.crabime.mq.practice.rocketmq.core.mq.hook.DefaultConsumeMessageHook;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPullConsumerImpl;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static cn.crabime.mq.practice.rocketmq.core.Constant.NORMAL_PULL_CONSUMER;
import static cn.crabime.mq.practice.rocketmq.core.Constant.TOPIC_PULL_NORMAL_ORDER;

@Component
public class MQPullMessageListener implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(MQPullMessageListener.class);

    private static ConcurrentHashMap<MessageQueue, Long> messageQueueOffsetOrder = new ConcurrentHashMap<>();

    private static DefaultMQPullConsumer pullConsumer = null;

    static {
        try {
            pullConsumer = new DefaultMQPullConsumer(NORMAL_PULL_CONSUMER);
            pullConsumer.setNamesrvAddr("47.103.3.149:9876");
            DefaultMQPullConsumerImpl defaultMQPullConsumerImpl = pullConsumer.getDefaultMQPullConsumerImpl();
            defaultMQPullConsumerImpl.registerConsumeMessageHook(new DefaultConsumeMessageHook());
            // consumer必须先启动
            pullConsumer.start();
        } catch (MQClientException e) {
            logger.error("pullConsumer获取topic[" + TOPIC_PULL_NORMAL_ORDER + "]异常", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        Timer timer = new Timer("PullTimer", true);
        // timer.scheduleAtFixedRate(new PullTask(), 0L, 30000L);
    }


    class PullTask extends TimerTask {
        @Override
        public void run() {
            try {

                Set<MessageQueue> messageQueues = pullConsumer.fetchSubscribeMessageQueues(TOPIC_PULL_NORMAL_ORDER);
                logger.info("队列长度:{}", messageQueues.size());

                for (MessageQueue queue : messageQueues) {
                    logger.info("当前队列{}", queue);

                    // currentQueueOffset为NULL会导致下一步pull操作空指针，在当前环境下无法debug
                    Long currentQueueOffset = messageQueueOffsetOrder.get(queue) == null ? 0L : messageQueueOffsetOrder.get(queue);
                    PullResult pullResult = pullConsumer.pull(queue, "*", currentQueueOffset, 32, 3000);

                    messageQueueOffsetOrder.put(queue, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
                            msgFoundList.forEach(new Consumer<MessageExt>() {
                                @Override
                                public void accept(MessageExt messageExt) {
                                    logger.info("pull消费端接收到消息:{}", new String(messageExt.getBody()));
                                }
                            });
                            break;
                        default:
                            logger.info("没有新消息...");
                            break;
                    }
                }
            } catch (MQClientException e) {
                logger.error("pullConsumer获取topic[" + TOPIC_PULL_NORMAL_ORDER + "]异常", e);
            } catch (MQBrokerException e) {
                logger.error("pullConsumer获取队列信息异常", e);
            } catch (InterruptedException e) {
                logger.error("pull阻塞过程线程被中断", e);
            } catch (RemotingException e) {
                logger.error("远程连接异常", e);
            }
        }
    }
}
