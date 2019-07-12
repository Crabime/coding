package env;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ApplicationClosedListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("应用关闭事件：" + event.getSource());
    }
}
