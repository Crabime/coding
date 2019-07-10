package env;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = "env")
public class AppConfig {

    @Bean
    @Profile("test")
    public World testWorld() {
        return new JapanWorld();
    }

    @Bean
    @Profile("prod")
    public World formalWorld() {
        return new ChinaWorld();
    }
}
