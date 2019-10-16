package cn.crabime.dubbo.pay;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class PayMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext atx = new ClassPathXmlApplicationContext("classpath:pay-dubbo1.xml");
        atx.start();


        System.in.read();
    }
}
