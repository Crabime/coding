package autowire.b;

import autowire.a.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("autowire.b")
public class BConfig {

    @Bean
    public Student student() {
        return new Student("张三", "三年级");
    }
}
