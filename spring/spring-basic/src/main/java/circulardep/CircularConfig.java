package circulardep;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:p1.properties")
@ComponentScan(basePackages = "circulardep", excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = ExcludeTypeFilter.class))
public class CircularConfig {
}
