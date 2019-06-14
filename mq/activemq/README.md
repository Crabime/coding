ActiveMQ学习历程
================

测试Spring对jms支持时，maven运行脚本如下：
<pre><code>mvn test -Dtest=SpringContextTest</pre></code>
这样可以避免运行没必要的测试用例

最开始项目为一个简单的java项目，但是为了学习ActiveMQ在web service中的应用，这里将普通maven转换为dynamic web service结构：
*普通maven项目转换为Web maven工程目录如下*：
***
right click project --> properties --> project facets --> choose dynamic web service
***

*增加ActiveMQ Spring支持*
工程已改成web service后，很多之前写的main方法不再像之前那样运行，可以通过maven进行单元测试。这里要说的是ActiveMQ对Spring的支持
现将定义好的beans.xml文件放到*src/test/resources*目录下，然后执行`mvn test -Dtest=SpringContextTest`来运行某一个具体的测试类，避免mvn一下将test目录下所有的测试类都运行完。
### maven编译、打包，但是不想要运行测试类 ###
`clean compile install -X -DskipTests`