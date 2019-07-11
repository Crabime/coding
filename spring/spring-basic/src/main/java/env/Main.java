package env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

    @Autowired
    private World world;

    public void start() {
        world.sayHello();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext();
        atx.register(AppConfig.class);
//        atx.getEnvironment().addActiveProfile("prod");
        atx.refresh();
        Main main = atx.getBean(Main.class);
        main.start();
    }
}
