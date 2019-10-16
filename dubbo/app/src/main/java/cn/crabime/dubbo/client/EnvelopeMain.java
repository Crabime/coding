package cn.crabime.dubbo.client;

import cn.crabime.dubbo.server.service.EnvelopeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnvelopeMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        context.start();

        EnvelopeService envelopeService = (EnvelopeService) context.getBean("envelopeService");
        envelopeService.sendEnvelope("crabime", 10, 15);
    }
}
