package cn.crabime.mvc.basic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.*;

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
        super.configureContentNegotiation(configurer);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
