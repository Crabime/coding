package cn.crabime.topic;

import java.io.Serializable;
import java.util.Random;

class SenderThread implements Runnable {

    private MQDemoTopicSender sender;

    private Serializable serializable;

    private String topic;

    public SenderThread(MQDemoTopicSender sender, String topic) {
        this(sender, topic, null);
    }

    public SenderThread(MQDemoTopicSender sender, String topic, Serializable serializable) {
        this.sender = sender;
        this.topic = topic;
        this.serializable = serializable;
    }

    private String generateMessage() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetLength = 10;
        Random random = new Random();
        StringBuilder builder = new StringBuilder(targetLength);
        for (int i = 0; i < targetLength; i++) {
            int randomLimitInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit));
            builder.append((char)randomLimitInt);
        }
        String randomString = builder.toString();
        return randomString;
    }


    @Override
    public void run() {
        if (null != serializable) {
            this.sender.sendSerializableMessage(this.serializable, this.topic);
        } else {
            // 创建一个随机字符串，并采用发送字符串的api
            String mes = generateMessage();
            this.sender.sendString(mes, this.topic);
        }
    }
}
