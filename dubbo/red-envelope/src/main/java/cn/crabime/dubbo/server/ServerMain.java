package cn.crabime.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        String configFileName = args[0];

        ClassPathXmlApplicationContext atx = new ClassPathXmlApplicationContext("classpath:" + configFileName + ".xml");
        atx.start();

        System.in.read();
    }
}
