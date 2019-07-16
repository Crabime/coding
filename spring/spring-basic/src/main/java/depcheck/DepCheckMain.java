package depcheck;

import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DepCheckMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(GradeConfig.class);
        atx.getBeanFactory().addBeanPostProcessor(new RequiredAnnotationBeanPostProcessor());
        Grade gr = atx.getBean(Grade.class);
        System.out.println(gr.getDirector().getName());
    }
}
