package lazy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class LazyAppConfig {

    @Bean("obj")
    public NormalObject normalObject() {
        return new NormalObject("Jack", 21);
    }

    @Bean("obj")
    public BeanSameNameNormalObject beanSameNameNormalObject() {
        return new BeanSameNameNormalObject();
    }

    @Lazy
    public BigObject bigObject() {
        return new BigObject("Rose");
    }
}
