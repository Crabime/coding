SpringMVC之yaml数据交换格式实现
===
总所周知，spring mvc默认是支持json/xml数据交换格式的，那如果用yml做前后台数据交换又该怎样呢？接下来将一一介绍如何在Spring MVC中注册
一个新的数据交换格式及应用。

### 实现yaml消息转换器
因为yaml在spring中没有对应的转换器，所以需要重新实现一个，源码参考[YamlMessageConverter](src/main/java/cn/crabime/mvc/basic/YamlMessageConverter.java)
首先需要定义一个构造器，将需要使用的MIME-TYPE注册到该转换器中。如果这里不注册，那请求返回<strong>406</strong>且spring mvc无日志输出，
这样很不利于调错，spring mvc内部会抛出一个`HttpMediaTypeNotSupportedException`异常。然后就是实现抽象父类的几个抽象方法，对请求发送过来的消息进行编解码。

### mvc容器中注册消息转换器
其次在[WebMvcConfigurerAdapter](src/main/java/cn/crabime/mvc/basic/MvcConfig.java)中注册消息转换器，通过实现下面两个方法进行
转换器注册：
* `configureContentNegotiation`:让spring mvc能识别<strong>".yml"</strong>尾缀，并交给`YamlMessageConverter`转换器解析传入的参数
* `extendMessageConverters`:不抛弃spring原有的转换器基础上增量增加当前yml转换器，默认注册了哪些转换器可以参考[WebMvcConfigurationSupport#addDefaultHttpMessageConverters]("https://github.com/spring-projects/spring-framework/blob/53d067399d3818ba3160010716a1012ca6abd5ba/spring-webmvc/src/main/java/org/springframework/web/servlet/config/annotation/WebMvcConfigurationSupport.java#L833")源码

### controller中指定consume MediaType
`controller`代码参见:[IndexController#getYamlRandomBean](src/main/java/cn/crabime/mvc/basic/IndexController.java)，该接口
接收请求数据类型为`text/yaml`，返回数据类型为`application/json`。这里解释一下spring mvc中consume和produce作用：
* <strong>consumes</strong>:接口能接收的请求数据类型，对应前台的ajax请求的contentType
* <strong>produces</strong>:接口会返回的请求数据类型，对应前台的accept请求头
浏览器截图及解释如下：

<img src="http://pv89or3o8.bkt.clouddn.com/ajax-request-response.png" />

### 前台如何写
参考文件[index.jsp](src/main/webapp/WEB-INF/views/index.jsp)