package cn.crabime.mvc.basic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.XmlViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cn.crabime.mvc.basic")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 不支持后置模糊匹配，/abc匹配正常，但/abc.*返回404
        configurer.setUseSuffixPatternMatch(true);
        // 不支持后面的正斜线，如/abc匹配正常，/abc/返回404
        configurer.setUseTrailingSlashMatch(false);
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // Spring MVC默认是对URL大小写敏感的，这里设置为不敏感
        pathMatcher.setCaseSensitive(false);
        configurer.setPathMatcher(pathMatcher);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // 以.crabime结尾的URL也将被解析为json
        configurer.mediaType("crabime", MediaType.APPLICATION_JSON);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

//        converters.add(xxx);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        XmlViewResolver xvr = new XmlViewResolver();
        xvr.setLocation(new ClassPathResource("views.xml"));
        registry.viewResolver(xvr);
//        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
