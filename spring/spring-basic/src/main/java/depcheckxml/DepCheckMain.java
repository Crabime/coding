package depcheckxml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DepCheckMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext atx = new ClassPathXmlApplicationContext("classpath:depcheck.xml");
        Grade grade = atx.getBean(Grade.class);
        System.out.println(grade.getDirector().getName());
    }
}
