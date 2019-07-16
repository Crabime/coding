package depcheck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "depcheck")
public class GradeConfig {

    @Bean
    public Grade grade() {
        Grade grade = new Grade();
        grade.setDirector(new Director("张三"));
        return grade;
    }
}
