package cn.crabime.mq.practice.rocketmq.core;

public class Constant {

    public final static String TRANSACTION_PRODUCER = "transaction_producer";

    public final static String TRANSACTION_CONSUMER = "transaction_consumer";

    public final static String NORMAL_PRODUCER = "normal_producer";

    public final static String NORMAL_CONSUMER = "normal_consumer";

    public final static String NORMAL_PULL_CONSUMER = "normal_pull_consumer";

    public final static String TRANSACTION_MESSAGE_ARGUMENT = "tq";

    public final static String TOPIC_NORMAL_TRANSACTION_MESSAGE = "TopicTransaction";

    public final static String TOPIC_NORMAL_ORDER = "orders";

    public final static String TOPIC_TAG_SUBSCRIPTION = "*";

    public final static String TOPIC_PULL_NORMAL_ORDER = "pull_order";

    public final static String TAG_TRANSACTION_PREFIX = "Transaction";

    public final static String HOOK_PULL_CONSUME_MESSAGE = "consume_hook";
}
