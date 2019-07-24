package circulardep;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:p1.properties")
@ComponentScan(basePackages = "circulardep")
public class CircularConfig {
// TODO: 2019/7/24 增加excludeFilter配置
}
