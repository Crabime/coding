SpringMVC处理流程
===
### Spring MVC架构图

下图是我从Google上Copy了一份架构图，通过这份架构图，再一起来debug源码，这样能够更深刻的理解Spring MVC运作原理.
<img src="http://pv89or3o8.bkt.clouddn.com/RequestLifecycle.png" />

### Spring MVC启动时如何处理`@Controller`、`@RequestMapping`注解
Spring启动时会扫描当前容器中所有`Bean`，实例化之前会执行`InitializingBean#afterPropertiesSet`方法，这里可以先看
`AbstractHandlerMethodMapping`类。该类实现了`InitializingBean`接口，`afterPropertiesSet`方法源码如下：
```
@Override
public void afterPropertiesSet() {
    initHandlerMethods();
}
protected void initHandlerMethods() {
    // 获取到当前spring容器中所有beanName
    String[] beanNames = (this.detectHandlerMethodsInAncestorContexts ?
            BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) :
            getApplicationContext().getBeanNamesForType(Object.class));

    for (String beanName : beanNames) {
        if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
            Class<?> beanType = null;
            try {
                // 获取该bean class类型
                beanType = getApplicationContext().getType(beanName);
            }
            catch (Throwable ex) {
                // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                }
            }
            // 判断当前这个bean是否有@Controller、@RequestMapping注解标识，若有则是一个标准的spring mvc handler
            if (beanType != null && isHandler(beanType)) {
                // 获取到controller下的handlerMethod
                detectHandlerMethods(beanName);
            }
        }
    }
    handlerMethodsInitialized(getHandlerMethods());
}
```

### `HandlerMethod`、`HandlerAdapter`、`HandlerMapping`、`HandlerInterceptor`、`HandlerExecutionChain`
* `HandlerMethod`：封装了`beanName`与`method`，进一步细化调用地方
* `HandlerAdapter`：在`DispatcherServlet#doDispatcher`中先找到请求对应的`HandlerAdapter`，执行`HandlerAdapter#handle`方法生成`ModelAndView`
* `HandlerMapping`：启动时在`WebMvcConfigurationSupport`中注册
* `HandlerInterceptor`：
* `HandlerExecutionChain`：


### `Spring MVC`启动时默认加载的五个`HandlerMapping`
1. `RequestMappingHandlerMapping`：将`@RequestMapping`注解对应的方法转换为`RequestMappingInfo`
2. `viewControllerHandlerMapping -> WebMvcConfigurationSupport$EmptyHandlerMapping`：将URL直接映射为视图名字
3. `BeanNameUrlHandlerMapping`：传入URL找到对应`Bean`
4. `resourceHandlerMapping`：处理静态资源映射
5. `defaultServletHandlerMapping`：用来处理静态资源，处理方式将请求转发给servlet容器中**default** servlet。注意它可以映射"/"，
也就是说它可以映射所有路径，但是由于它的优先级最低，所以当前面`handlerMapping`均映射失败时才会转交给它。


### 如何在一个`controller`中找到合适的`HandlerMethod`
因为一个`@Controller`标注的类里面可能会有很多的方法，但并不是每一个方法都有`@RequestMapping`注解标识，也就是说并不是每个方法都是一个
`HandlerMethod`，那Spring如何确定一个`HandlerMethod`呢？先看如下发现`HandlerMethod`方法：
```
protected void detectHandlerMethods(final Object handler) {
    // handler传入的就是一个controller对应的beanName，这里只是获取它的Class类型
    Class<?> handlerType = (handler instanceof String ?
            getApplicationContext().getType((String) handler) : handler.getClass());
    final Class<?> userType = ClassUtils.getUserClass(handlerType);

    // 获取controller中符合要求的方法与该方法对应的URL映射
    Map<Method, T> methods = MethodIntrospector.selectMethods(userType,
            new MethodIntrospector.MetadataLookup<T>() {
                @Override
                public T inspect(Method method) {
                    try {
                        return getMappingForMethod(method, userType);
                    }
                    catch (Throwable ex) {
                        throw new IllegalStateException("Invalid mapping on handler class [" +
                                userType.getName() + "]: " + method, ex);
                    }
                }
            });

    for (Map.Entry<Method, T> entry : methods.entrySet()) {
        // 就是待执行的方法
        Method invocableMethod = AopUtils.selectInvocableMethod(entry.getKey(), userType);
        // 返回RequestMappingInfo，记录RequestMapping中的信息
        T mapping = entry.getValue();
        // 记录当前这个controller、它里面可执行的http请求URL、执行该URL需要调用controller中的方法一起注册到spring mvc中
        registerHandlerMethod(handler, invocableMethod, mapping);
    }
}
```

