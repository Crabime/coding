package env;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext();
        atx.register(AppConfig.class);
        atx.addApplicationListener(new ApplicationStartedListener());
        atx.addApplicationListener(new ApplicationClosedListener());
//        atx.getEnvironment().addActiveProfile("prod");
        String[] activeProfiles = atx.getEnvironment().getActiveProfiles().length == 0 ?
                atx.getEnvironment().getDefaultProfiles() : atx.getEnvironment().getActiveProfiles();
        System.out.println("当前启动的profile有：" + Arrays.toString(activeProfiles));
        atx.refresh();
        World main = atx.getBean(World.class);
        main.sayHello();
        Thread.sleep(2000);
        atx.registerShutdownHook();
    }
}
