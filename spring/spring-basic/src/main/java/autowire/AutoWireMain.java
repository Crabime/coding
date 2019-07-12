package autowire;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoWireMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(AutowireConfig.class);
        Teacher teacher = atx.getBean(Teacher.class);
        System.out.println(teacher.getStudent().getGrade());
    }
}
