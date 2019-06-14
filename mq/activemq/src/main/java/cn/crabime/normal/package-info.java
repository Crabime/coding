/**
 * 测试给予JMS同步发送消息机制
 * 普通消息发送，也就是生产者、消费者模型
 * 生产者生产多少，消费者就消费多少。
 * 他们不用规定topic、cn.crabime.queue，只需要规定一个共同的Destination
 */
package cn.crabime.normal;