package cn.crabime.config;

import cn.crabime.beans.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cn.crabime")
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * SPEL表达式在处理bean时都有默认的converter和formatter，如User中有School属性，那么在前台我们可以通过
     * ${user.school.name}来获取用户的校名，但是如果我要获取学校的具体信息，也就是类似于String.format方法
     * 可以通过GenericConversionService来完成，在WebMVC中注册Formatter通过FormatterRegistry来完成
     * controller例子见SampleController：/suview
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        SchoolFormatter schoolFormatter = new SchoolFormatter();
        registry.addFormatter(schoolFormatter);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        StringBuilder builder = new StringBuilder();
//        for (HttpMessageConverter mc : messageConverters) {
//            builder.append(mc.getClass().getName());
//            builder.append(", ");
//        }
        logger.info("来到configureMessageConverters方法中！" + builder.toString());
        return;
    }

    /**
     * 如果重写了configureDefaultServletHandling方法，无法处理的请求会forward到'default' servlet上，
     * 通常使用场景是对静态资源的访问，如果没有重写addResourceHandlers方法，对请求"/static/**"会通过DefaultServlet
     * forward到/static/目录下。如果"/static/**"请求对应的实际路径为物理机上的绝对路径如"/data/abc/"或
     * 相对路径非static目录下，如"/abc/static/"路径，那么通过DefaultServlet是无法完成的，这个时候必须要
     * 重写下面的addResourceHandlers方法来完成
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Spring MVC添加静态资源管理器
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
//    }

    /**
     * 注册Spring MVC默认的InternalViewResolver
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @PostConstruct
    public void getSpringDefaultMessageConverter() {
        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        StringBuilder builder = new StringBuilder();
        for (HttpMessageConverter mc : messageConverters) {
            builder.append(mc.getClass().getName());
            builder.append(", ");
        }
        logger.info("来到getSpringDefaultMessageConverter方法中！" + builder.toString());
    }
}
