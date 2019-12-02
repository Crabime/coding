package cn.crabime.mq.practice.rocketmq.core.mq.hook;

import org.apache.rocketmq.client.hook.ConsumeMessageContext;
import org.apache.rocketmq.client.hook.ConsumeMessageHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.crabime.mq.practice.rocketmq.core.Constant.HOOK_PULL_CONSUME_MESSAGE;

public class DefaultConsumeMessageHook implements ConsumeMessageHook {

    private final static Logger logger = LoggerFactory.getLogger(DefaultConsumeMessageHook.class);

    @Override
    public String hookName() {
        return HOOK_PULL_CONSUME_MESSAGE;
    }

    @Override
    public void consumeMessageBefore(ConsumeMessageContext context) {
        logger.info("consume前置处理");
    }

    @Override
    public void consumeMessageAfter(ConsumeMessageContext context) {
        logger.info("consume后置处理");
    }
}
