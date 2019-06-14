package cn.crabime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by crabime on 6/20/17.
 * 不使用XML方式管理bean
 */
public class SpringAnnotationConfigTest {
    private ApplicationContext ctx = null;
    @Before
    public void setUp(){
        //获取Spring注解配置类
        ctx = new AnnotationConfigApplicationContext(SpringAnnotationContainer.class);
    }

    /**
     * 单纯的测试Spring注解实现bean定义方式
     */
    @Test
    public void testSpringAnnotationConfiguration(){
        Person person = ctx.getBean("person", Person.class);
        System.out.println("定义在Spring容器中的person名字为:" + person.getName());
    }

    /**
     * 以Spring注解为主,XML为辅的方式,同时运行一二两个测试类
     * 第一个测试类中Person定义在注解中
     * 第二个测试类复合属性bean定义在XML文件中
     * 运行命令:mvn test -Dtest=SpringAnnotationConfigTest
     */
    @Test
    public void testSpringAnnotationFirstConfiguration(){
        AssemblePropertyBean assembleBean = ctx.getBean("assembleBean", AssemblePropertyBean.class);
        Person person = assembleBean.getPerson();
        System.out.println("在AssembleBean中定义的Person所对应的name属性值为:" + person.getName());
    }
}
