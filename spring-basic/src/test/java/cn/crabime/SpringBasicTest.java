package cn.crabime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by crabime on 6/19/17.
 * Spring基本用法测试
 */
public class SpringBasicTest {
    private ApplicationContext ctx = null;
    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("beans.xml");
    }

    /**
     * 测试Spring国际化支持
     */
    @Test
    public void testSpringGlobalization(){
        String hello = ctx.getMessage("hello", new String[]{"孙悟空","中国武汉"}, Locale.CHINA);
        System.out.println("来自messages.properties中定义的hello输出为:" + hello);
    }

    /**
     * 测试bean获取spring容器
     */
    @Test
    public void testApplicationContextAware(){
        ApplicationContextAwareTest test = ctx.getBean("applicationAwareTest", ApplicationContextAwareTest.class);
        test.teacherCheck();
    }

    /**
     * 测试在beans.xml文件中对bean使用autowire属性
     */
    @Test
    public void testAutowired(){
        Teacher teacher = ctx.getBean("teacher", Teacher.class);
        teacher.check();
    }

    /**
     * 测试Spring复合属性bean
     */
    @Test
    public void testSpringAssembleBean(){
        AssemblePropertyBean assembleBean = ctx.getBean("assembleBean", AssemblePropertyBean.class);
        Person person = assembleBean.getPerson();
        System.out.println("在AssembleBean中定义的Person所对应的name属性值为:" + person.getName());
    }

    /**
     * 使用Spring FieldRetrievingFactoryBean获取静态常量值
     */
    @Test
    public void testGetClassStaticFieldValue(){
        System.out.println("系统获取到的java.sql.Connection静态常量值为:" + ctx.getBean("transaction_serializable"));
        //直接通过FieldRetrievingFactoryBean的setStaticField获取静态常量值
        System.out.println("==>使用该方式获取到的值为:" + ctx.getBean("transaction_serializable_direct"));
        //使用util命名空间获取某一个静态常量值,简化spring配置
        System.out.println("==>使用util命名空间获取到的值为:" + ctx.getBean("transaction_serializable_util"));
    }

    /**
     * 使用spring EL表达式
     */
    @Test
    public void testSpringELExpression(){
        ExpressionParser parser = new SpelExpressionParser();
        Person person = new Person();
        person.setName("China Asteroid");
        Expression exp = parser.parseExpression("name");
        System.out.println("以person为root,name表达式的值是:" + exp.getValue(person, String.class));

        StandardEvaluationContext sec = new StandardEvaluationContext();
        sec.setRootObject(person);
        System.out.println("==>使用同样的方式解析Person类的name属性值:" + exp.getValue(sec, String.class));

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(true);
        EvaluationContext ec = new StandardEvaluationContext();
        ec.setVariable("list", booleanList);
        System.out.println("解析list集合中第一个布尔值为:" + parser.parseExpression("#list[0]").getValue(ec));
        parser.parseExpression("#list[0]").setValue(ec, "false");
        //这里会导致空指针异常,因为没有第二个值
//        System.out.println("设置集合中第二个不存在的值为:" + parser.parseExpression("#list[1]").getValue(ec));
        System.out.println("设置集合中第一个不存在的值为:" + parser.parseExpression("#list[0]").getValue(ec));
    }
}
