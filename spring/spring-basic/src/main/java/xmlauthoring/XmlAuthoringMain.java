package xmlauthoring;

import cn.crabime.xml.authoring.ComplexComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XmlAuthoringMain {

    public static void main(String[] args) {
        ApplicationContext atx = new ClassPathXmlApplicationContext("classpath:authoring.xml");
        SimpleDateFormat dateFormat = (SimpleDateFormat) atx.getBean("df");
        String dateResult = dateFormat.format(new Date());
        System.out.println(dateResult);

        dateFormat = (SimpleDateFormat) atx.getBean("df1");
        dateResult = dateFormat.format(new Date());
        System.out.println(dateResult);


        ComplexComponent cc = (ComplexComponent) atx.getBean("mc1");
        System.out.println(cc.getName());
        List<ComplexComponent> complexComponents = cc.getComplexComponents();
        System.out.println(complexComponents.size());
        System.out.println(complexComponents.get(0).getName().equals("father"));

    }
}
