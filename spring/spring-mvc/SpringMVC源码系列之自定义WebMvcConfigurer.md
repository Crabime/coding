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
为了更好的看到spring报错信息，这里引入log4j框架。注意：由于spring使用的日志框架是`commons-logging`，这里我用的是`slf4j`，所以这里
需要导入如下三个包，对这块不了解可以参考[java日志组件介绍（common-logging，log4j，slf4j，logback ）]("https://blog.csdn.net/yycdaizi/article/details/8276265")：
```
<!-- slf4j核心包 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>
<!-- commons logging与slf4j桥接库 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
</dependency>
<!-- 解决slf4j NOP问题 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
</dependency>
```
同时记着把这些包放到tomcat `classpath`下，`Idea`为`artifact`中。
在[`IndexController`](src/main/java/cn/crabime/mvc/basic/IndexController.java)中增加一个返回`json`格式数据的接口.
在[前台页面](src/main/webapp/WEB-INF/views/index.jsp)中增加一个`Ajax`请求接口，用于自定义请求。
正常`Ajax`请求如下，**URL:http://localhost:8080/mvc/grb**：
```
$("#get-random-json").click(function () {
    $.ajax({
        url: "${pageContext.servletContext.contextPath}/grb",
        type: "GET",
        success: function (data) {
            var name = data.name;
            var age = data.age;
            $("#random-json-text").text("name:" + name + ", age:" + age);
        }
    })
})
```
返回结果如下图：
<img src="http://pv89or3o8.bkt.clouddn.com/mvcconfigurer%E6%AD%A3%E5%B8%B8%E8%BF%94%E5%9B%9E%E6%95%B0%E6%8D%AE.png" />
**注意**：上面请求的URL没有带任何尾缀。
**URL:http://localhost:8080/mvc/grb.xml**。
返回结果如下图：
<img src="http://pv89or3o8.bkt.clouddn.com/unacceptable-header-406.png" />
可以看出，spring这个时候已经报错了，那这个URL是如何解析的呢？这里是spring mvc请求流程堆栈：
```
org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor.writeWithMessageConverters(AbstractMessageConverterMethodProcessor.java:184)  // 使用MessageConverter将返回值转换为Accept格式
	  at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.handleReturnValue(RequestResponseBodyMethodProcessor.java:174)
	  at org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:81) //处理RequestMapping注解对应HandlerMethod返回值
	  at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:113)
	  at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:827)
	  at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:738)
	  at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:85)
	  at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:967)
	  at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:901)
	  at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970)
	  at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:861)
	  at javax.servlet.http.HttpServlet.service(HttpServlet.java:622)
	  at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846)
	  at javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
	  at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:292)
	  at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:207)
	  at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	  at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:240)
	  at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:207)
	  at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:212)
	  at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:94)
	  at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:504)
	  at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:141)
	  at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)
	  at org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:620)
	  at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:88)
	  at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:502)
	  at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1132)
	  at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:684)
	  at org.apache.tomcat.util.net.AprEndpoint$SocketProcessor.doRun(AprEndpoint.java:2521)
	  at org.apache.tomcat.util.net.AprEndpoint$SocketProcessor.run(AprEndpoint.java:2510)
	  - locked <0x1710> (a org.apache.tomcat.util.net.AprEndpoint$AprSocketWrapper)
	  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	  at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	  at java.lang.Thread.run(Thread.java:748)
```
上述栈顶就是spring mvc对请求结果处理逻辑，下面是处理RequestMapping返回值解析核心代码：
```
protected <T> void writeWithMessageConverters(T value, MethodParameter returnType,
			ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

    Object outputValue;
    Class<?> valueType;
    Type declaredType;

    if (value instanceof CharSequence) {
        outputValue = value.toString();
        valueType = String.class;
        declaredType = String.class;
    }
    else {
        outputValue = value;
        valueType = getReturnValueType(outputValue, returnType);
        declaredType = getGenericType(returnType);
    }

    HttpServletRequest request = inputMessage.getServletRequest();
    // 获取HttpServletRequest中请求头中Accept或requestParameter中接收的返回值类型
    List<MediaType> requestedMediaTypes = getAcceptableMediaTypes(request);
    // 获取当前spring mvc环境中可以解析的所有数据类型
    List<MediaType> producibleMediaTypes = getProducibleMediaTypes(request, valueType, declaredType);

    if (outputValue != null && producibleMediaTypes.isEmpty()) {
        throw new IllegalArgumentException("No converter found for return value of type: " + valueType);
    }

    Set<MediaType> compatibleMediaTypes = new LinkedHashSet<MediaType>();
    // 如果请求的Accept数据类型为"application/xml"而spring mvc只能解析"application/json"，返回的compatibleMediaTypes就为空
    for (MediaType requestedType : requestedMediaTypes) {
        for (MediaType producibleType : producibleMediaTypes) {
            if (requestedType.isCompatibleWith(producibleType)) {
                compatibleMediaTypes.add(getMostSpecificMediaType(requestedType, producibleType));
            }
        }
    }
    // 如果没有注册合适的解析器就会抛出HttpMediaTypeNotAcceptableException异常
    if (compatibleMediaTypes.isEmpty()) {
        if (outputValue != null) {
            throw new HttpMediaTypeNotAcceptableException(producibleMediaTypes);
        }
        return;
    }
    ...
}
```
上面代码中`getAcceptableMediaTypes(request)`实现逻辑如下：
```
private List<MediaType> getAcceptableMediaTypes(HttpServletRequest request) throws HttpMediaTypeNotAcceptableException {
    // 使用contentNegotiationManager解析传过来的请求
    List<MediaType> mediaTypes = this.contentNegotiationManager.resolveMediaTypes(new ServletWebRequest(request));
    return (mediaTypes.isEmpty() ? Collections.singletonList(MediaType.ALL) : mediaTypes);
}
```
上面`contentNegotiationManager`调用`ContentNegotiationStrategy`来解析http请求，从`HttpServletRequest`中获取它想要返回的数据格式，
spring mvc默认的解析策略有如下两个：
1. `ServletPathExtensionContentNegotiationStrategy`:根据尾缀如：.xml或请求中携带format参数指定使用什么格式如：?format=xml
2. `HeaderContentNegotiationStrategy`:根据请求头中的`Accept`参数来判断

接着往下讲，那返回的数据格式又是怎么定的呢？看`getProducibleMediaTypes`方法调用过程：
```
protected List<MediaType> getProducibleMediaTypes(HttpServletRequest request, Class<?> valueClass, Type declaredType) {
    Set<MediaType> mediaTypes = (Set<MediaType>) request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
    if (!CollectionUtils.isEmpty(mediaTypes)) {
        return new ArrayList<MediaType>(mediaTypes);
    }
    else if (!this.allSupportedMediaTypes.isEmpty()) {
        List<MediaType> result = new ArrayList<MediaType>();
        // 获取到spring mvc中注册的所有message converter
        for (HttpMessageConverter<?> converter : this.messageConverters) {
            // GenericHttpMessageConverter将请求结果转化为目标对象并写入到http response中
            if (converter instanceof GenericHttpMessageConverter && declaredType != null) {
                if (((GenericHttpMessageConverter<?>) converter).canWrite(declaredType, valueClass, null)) {
                    result.addAll(converter.getSupportedMediaTypes());
                }
            }
            else if (converter.canWrite(valueClass, null)) {
                result.addAll(converter.getSupportedMediaTypes());
            }
        }
        return result;
    }
    else {
        return Collections.singletonList(MediaType.ALL);
    }
}
```
那这里的核心问题是spring mvc默认的`message converter`有哪些呢？由于我们在应用中并没有配置任何转换器，spring mvc怎么知道转换请求需要的数据格式，
这个时候可以看spring mvc默认converter注册地方：`WebMvcConfigurationSupport#addDefaultHttpMessageConverters`方法，代码如下：
```
protected final void addDefaultHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setWriteAcceptCharset(false);

    messageConverters.add(new ByteArrayHttpMessageConverter());
    messageConverters.add(stringConverter);
    messageConverters.add(new ResourceHttpMessageConverter());
    messageConverters.add(new SourceHttpMessageConverter<Source>());
    messageConverters.add(new AllEncompassingFormHttpMessageConverter());

    if (romePresent) {
        messageConverters.add(new AtomFeedHttpMessageConverter());
        messageConverters.add(new RssChannelHttpMessageConverter());
    }

    // 如果classpath中包含jackson-dataformat-xml依赖，则加载xml格式转换器
    if (jackson2XmlPresent) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.xml().applicationContext(this.applicationContext).build();
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter(objectMapper));
    }
    else if (jaxb2Present) {
        messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
    }

    // 如果classpath中有jackson-databind依赖，则注册MappingJackson2HttpMessageConverter转换器，如果有gson依赖，则注册gson转换器
    if (jackson2Present) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }
    else if (gsonPresent) {
        messageConverters.add(new GsonHttpMessageConverter());
    }
}
```
综上所述：tomcat 406错误体现就是spring mvc中没有xml对应转换器，换句话说没有`jackson-dataformat-xml`依赖，这个时候将该依赖添加到pom.xml中，
并添加到tomcat classpath中，redeploy后会发现，接口又返回成功了，前面`Ajax`返回结果如下：
<img src="http://pv89or3o8.bkt.clouddn.com/spring-mvc-xml-converter.png" />
