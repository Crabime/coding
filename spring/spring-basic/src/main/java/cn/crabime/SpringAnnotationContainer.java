package cn.crabime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by crabime on 6/20/17.
 * 提出Spring容器初始化各个bean这种方式,通过纯注解方式去实现XML功能
 * 测试类详见SpringAnnotationConfigTest.java文件
 *
 * 如果是以XML为主,配置类为辅的方式,那么只需要在beans.xml文件中定义一个context:annotation-config
 * 然后将该bean定义到spring容器中(无需指定id属性)即可!
 */
@Configuration
@ImportResource(value = "classpath:beans.xml") //以注解为主、xml为辅的方式加载工程中所有bean
public class SpringAnnotationContainer {
    @Value("China Asteroid")
    private String personName;

    @Bean(name = "person")
    public Person newPerson(){
        Person person = new Person();
        person.setName(this.personName);
        return person;
    }
}
