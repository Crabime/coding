SpringMVC如何将请求交给DispatcherServlet处理
===
### `Spring MVC`如何注册`DispatcherServlet`
前面通过一篇文章讲过[Spring MVC应用如何被Tomcat发现](IDEA启动web项目无任务报错且启动页面404.md)，在执行`WebApplicationInitializer`
子类`AbstractDispatcherServletInitializer#onStartup`方法时，会调用`registerDispatcherServlet`方法，这里会创建一个`DispatcherServlet`
然后注册到`servlet`容器中，详细代码如下：
```
// 将DispatcherServlet注册到servlet容器中
protected void registerDispatcherServlet(ServletContext servletContext) {
    String servletName = getServletName();
    Assert.hasLength(servletName, "getServletName() must not return empty or null");

    // Spring MVC默认在ContextLoader中创建XmlWebApplicationContext
    WebApplicationContext servletAppContext = createServletApplicationContext();
    Assert.notNull(servletAppContext,
            "createServletApplicationContext() did not return an application " +
            "context for servlet [" + servletName + "]");

    // 创建DispatcherServlet，其实就是一个HttpServlet
    FrameworkServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
    dispatcherServlet.setContextInitializers(getServletApplicationContextInitializers());

    // 将创建的Servlet添加到Servlet容器中
    ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
    Assert.notNull(registration,
            "Failed to register servlet with name '" + servletName + "'." +
            "Check if there is another servlet registered under the same name.");

    registration.setLoadOnStartup(1);
    // getServletMapping由开发者自己继承AbstractAnnotationConfigDispatcherServletInitializer来实现，标识DispatcherServlet需要
    // 匹配哪些请求，一般"/"即可
    registration.addMapping(getServletMappings());
    registration.setAsyncSupported(isAsyncSupported());

    // 注册过滤器
    Filter[] filters = getServletFilters();
    if (!ObjectUtils.isEmpty(filters)) {
        for (Filter filter : filters) {
            registerServletFilter(servletContext, filter);
        }
    }

    customizeRegistration(registration);
}
```
从上面可以知道，`Spring MVC`里面只有一个`Servlet`，通过该`Servlet`来拦截所有所有http请求。

### 请求流程
