package cn.crabime.mq.practice.rocketmq.core.mq;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionExecutorImpl implements LocalTransactionExecuter {

    private final static Logger logger = LoggerFactory.getLogger(TransactionExecutorImpl.class);

    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        logger.info("msg={}, arg={}", new String(msg.getBody()), arg);
        String tags = msg.getTags();

        if (tags.equals("Transaction1")) {
            logger.warn("业务处理失败，进行回滚");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
