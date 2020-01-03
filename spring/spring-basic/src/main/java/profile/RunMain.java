package profile;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class RunMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment configurableEnvironment = atx.getEnvironment();
        configurableEnvironment.setActiveProfiles("jog");
        atx.setEnvironment(configurableEnvironment);
        atx.scan("profile");
        atx.refresh();
        RunInterface run = atx.getBean(RunInterface.class);
        run.run();

        try {
            LongDistanceRun longDistanceRun = atx.getBean(LongDistanceRun.class);
            longDistanceRun.longRun();
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
        }
    }
}
