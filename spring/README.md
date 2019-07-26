## Spring基础操作

### Spring如何解决环形引用
那什么是环形引用呢？见如下图：
<img src="http://pv89or3o8.bkt.clouddn.com/circular-deps.png" />
也就是说beanA中使用@Autowire注解注入beanB，同时在beanB
中又使用@Autowire注解注入了beanA，那spring在初始化bean时，先初始化beanA发现beanA中有依赖beanB，
然后开始初始化beanB，beanB有依赖beanA，这个时候发现beanA已经在创建过程中了，于是**循环依赖**
就发生了。

### 循环依赖例子
详细DEMO常见参见/spring/spring-basic/circulardep包,描述了树与啄木鸟之间关系。
**注意:要产生循环依赖必须在构造方法上使用@Autowire注解**

### 源码分析spring如何解决循环依赖
这里我通过上面circulardep包下类debug源码，进一步分析spring如何帮助我们解决循环依赖问题。
详情见：[spring如何解决循环依赖原理](spring如何解决循环依赖.md)

### IDEA中项目部署无任务异常抛出，且首页404
[IDEA中项目部署无任务异常抛出，且首页404](spring-mvc/IDEA启动web项目无任务报错且启动页面404.md)