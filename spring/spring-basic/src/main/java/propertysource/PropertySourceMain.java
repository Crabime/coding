package propertysource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.function.Consumer;

public class PropertySourceMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(PropertySourceConfig.class);
        IronWorker iw = atx.getBean(IronWorker.class);
        iw.introduce();

        // TODO: 2019/7/17 为什么在当前环境下拿不到前面属性值
        // 通过另一种形式获取workerName
//        String value = atx.getBeanFactory().resolveEmbeddedValue("icon.worker.name");
//        System.out.println(value);
        String workerName = atx.getEnvironment().getProperty("icon.worker.name");
        System.out.println("workerName=" + workerName);

        PropertySourcesPlaceholderConfigurer placeholderConfigurer = atx.getBean(PropertySourcesPlaceholderConfigurer.class);
        PropertySources appliedPropertySources = placeholderConfigurer.getAppliedPropertySources();
        appliedPropertySources.forEach(new Consumer<PropertySource<?>>() {
            @Override
            public void accept(PropertySource<?> propertySource) {
                System.out.println(propertySource.getName());
            }
        });
    }
}
