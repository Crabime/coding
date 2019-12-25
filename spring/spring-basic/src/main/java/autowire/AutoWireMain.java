package autowire;

import autowire.a.AConfig;
import autowire.b.BConfig;
import autowire.b.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoWireMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext at = new AnnotationConfigApplicationContext(AConfig.class);
        AnnotationConfigApplicationContext bt = new AnnotationConfigApplicationContext(BConfig.class);
        bt.setParent(at);
        // 从子容器中获取父容器bean，正常！
        Teacher teacher = bt.getBean(Teacher.class);
        System.out.println(teacher.getStudent().getGrade());

        Teacher teacherFromParentContainer = at.getBean(Teacher.class);
        System.out.println(teacherFromParentContainer.getStudent().getGrade());
    }
}
