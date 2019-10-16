package cn.crabime.dubbo.client;

import cn.crabime.dubbo.server.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * dubbo客户端，测试dubbo在某个服务挂掉情况下能否继续坚持下去
 */
public class RandomChosenConsumer {

    private static Logger logger = LoggerFactory.getLogger(RandomChosenConsumer.class);

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理

        while (true) {
            String reply = demoService.sayHello("你好");
            logger.info("服务端返回{}", reply);

            Thread.sleep(3000);
        }
    }
}
