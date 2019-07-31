Spring MVC WebMvcConfigurer如何解析
===

### `Spring MVC`中所有组件集中注册中心
*组件集中注册中心*是什么意思？说白了就是如果使用纯注解的方式启动项目，那注册在xml中的组件就转移到`WebMvcConfigurer`类的实现上了。
下面结合源码和实例一一解释，所有的组件的配置参见[MvcConfig.java](src/main/java/cn/crabime/mvc/basic)，详细源码见
当前目录下`spring-mvc`模块

### 那其中有哪些组件呢？
1. [路径匹配转换器`PathMatchConfigurer`](#1)
2. [`ContentNegotiationStrategy`配置](#2)


#### <span id="1">路径匹配转换器`PathMatchConfigurer`</span>
这里我贴出了`PathMatchConfigurer`配置代码
```
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
```
如果不满足于spring默认对URL解析方式，如/abc/==/abc，/ABC!=/abc，这里我们可以通过配置`PathMatchConfigurer`来完成，当然这里要解释的
是如果这里我们不配置`suffixPattern`/`trailingSlash`等，spring会有一套自己的默认配置。如spring会在`WebMvcConfigurationSupport`中
获取`PathMatchConfigurer`中配置的`PathMatcher`/`URLPathHelper`，如果没有配置，那它会new一个新的对象并存入spring容器中。
其它选项（`suffixPattern`/`trailingSlash`）默认值配置你会发现`PathMatchConfigurer`中并没有设置，他们默认值设置参考`RequestMappingHandlerMapping`.

#### <span id="2">`ContentNegotiationStrategy`配置</span>
什么是`ContentNegotiationStrategy`？它在Spring MVC中起到什么作用呢？