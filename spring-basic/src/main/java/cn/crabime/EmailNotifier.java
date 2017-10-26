package cn.crabime;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by crabime on 11/11/16.
 */
public class EmailNotifier implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof EmailEvent){
            EmailEvent event = (EmailEvent) applicationEvent;
            System.out.println("source name is :" + event.getName());
            System.out.println("source address is :" + event.getAddress());
        }else {
            System.out.println("nothing need to be done");
        }
    }
}
