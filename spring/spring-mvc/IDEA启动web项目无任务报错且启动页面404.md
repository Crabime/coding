IDEA中项目部署无任务异常抛出，且首页404
===
相信很多使用IDEA的用户都碰到题目说的情况，启动时控制台一切正常，浏览器首页显示404。这里我写了一个[微型web项目](/spring/spring-mvc),
采用全注解的方式，先创建一个artifact，然后"edit configuration" -> "add new 'Tomcat Server' configuration" -> 'local' -> ...
上面步骤执行完后开始运行项目：

### 运行的console界面
<img src="http://pv89or3o8.bkt.clouddn.com/error-tomcat.png" />
可以发现项目很快就结束了，spring日志输出竟然没有，然后弹出的web界面显示404，相信很多人在这个时候显得有点束手无策。

### 解决办法
步骤：'project-structure' -> 'artifact' -> 'put into /WEB-INF/lib'。问题解决！
界面如下：
<img src="http://pv89or3o8.bkt.clouddn.com//spring/put-into-jar.pngchose-all-jars.png" />

### 问题产生原因
通过spring mvc源码我们可以看到一个核心的类`org.springframework.web.SpringServletContainerInitializer`，该类继承
自`ServletContainerInitializer`，查看该类注释可以看到如下：
> Interface which allows a library/runtime to be notified of a web application's startup phase and perform any required 
> programmatic registration of servlets, filters, and listeners in response to it.

也就是说tomcat在启动中找`ServletContainerInitializer`实现类，如果有并且有`HandlesTypes`注解标识，则tomcat会去加载该web应用。而
`spring mvc`肯定是实现了该接口，也就是上面的`SpringServletContainerInitializer`类。他是我们应用能否加载的核心，所以`WEB-INF/lib`
中一旦缺少`spring-web.jar`后，哪怕你其它的约束都遵循`servlet协议`，应用仍然无法加载，放进去就OK了！

### 模拟不添加`spring-web.jar`
我们还是按照上面方式将所有jar包放入`/WEB-INF/lib/`目录下，remove掉**spring-web**包，运行时会发现应用仍然无法加载，且报404错误，这
也就验证了我们上面的结论。