package propertysource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
//@PropertySource("classpath:p1.properties")
@ComponentScan(basePackages = "propertysource")
public class PropertySourceConfig {

    @Value("${icon.worker.name}")
    private String name;

    @Bean
    public IronWorker ironWorker() {
        return new IronWorker(name);
    }

    /**
     * 如果不使用PropertySource注解，这里可以使用静态Bean注入方式，将资源文件注入到当前Environment中
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource resource = new ClassPathResource("p1.properties");
        configurer.setLocation(resource);
        return configurer;
    }

}
