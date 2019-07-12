package env;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("应用启动事件" + event.getSource());
    }
}
