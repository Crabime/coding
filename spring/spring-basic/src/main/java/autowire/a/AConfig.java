package autowire.a;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("autowire.a")
public class AConfig {

    @Bean
    public Student student() {
        return new Student("张三", "三年级");
    }
}
