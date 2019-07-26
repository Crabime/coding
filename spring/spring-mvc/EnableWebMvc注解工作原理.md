`@EnableWebMvc`注解工作原理篇
===
查看spring源码发现该注解没有设置任何方法，也就是一个标识注解，spring官方文档是这样介绍的：

>Adding this annotation to an @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport

简单的说就是通过该注解，将`WebMvcConfigurationSupport`这个Spring MVC核心类注册到spring容器中。查看该类源码如下：
```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}
```
可以很清楚的看到，在注册该注解标示的类之前先注册`DelegatingWebMvcConfiguration`类。
**注意：`@EnableWebMvc`必须配合`@Configuration`注解一起使用**
