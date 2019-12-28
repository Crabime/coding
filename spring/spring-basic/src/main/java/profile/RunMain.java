package profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RunMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext();
//        ConfigurableEnvironment configurableEnvironment = atx.getEnvironment();
//        configurableEnvironment.setActiveProfiles("jog");
//        atx.setEnvironment(configurableEnvironment);
        atx.scan("profile");
        atx.refresh();
        RunInterface run = atx.getBean(RunInterface.class);
        run.run();
    }
}
