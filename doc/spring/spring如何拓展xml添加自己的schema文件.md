spring如何拓展xml添加自己的schema文件
===
使用过dubbo的同事应该都知道，dubbo有一套自己的xml namespace，将dubbo内部必要的配置类通过简单的xml配置暴露出来。那我们能不能自己
也写一套xsd，然后应用到spring xml配置文件中呢？当然可以！

##### 本文参考地址：[Extensible XML authoring](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/xml-custom.html)
##### [本文完整代码地址](/spring/xml-authoring)

### 要完成自定义xml拓展，需要如下几个步骤：
1. 创建xsd文件并将element、attribute定义好
2. 创建自己的`NamespaceHandler`
3. 创建自己的`BeanDefinitionParser`解析spring xml中配置
4. 将上面那些组件注册到`spring`中

### 以`java.text.SimpleDateFormat`为例
正常`SimpleDateFormat`注册如下：
```
<bean id="df1" class="java.text.SimpleDateFormat">
    <constructor-arg value="yyyy-MM-dd HH:mm" />
    <property name="lenient" value="true" />
</bean>
```

改造后spring xml配置文件片段如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:df="http://www.gooalgene.com/schema/dateformat"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.gooalgene.com/schema/dateformat http://www.gooalgene.com/schema/dateformat.xsd">

    <df:dateformat id="df" pattern="yyyy-MM-dd HH:mm" lenient="true" />
```

### 实现步骤如下：
#### 1.创建自己的xsd文件
xsd文件编写网上有很多教程，这里就不赘述了。这里我们需要的比较简单，只有属性且无嵌套，代码如下。但是注意：由于我们是要注册bean到spring容器中，
需要有`id`属性，所以这里我们导入了`spring-beans` xsd文件，使用地方在下方`identifiedType`部分。
完整[`dateformat.xsd`](/spring/xml-authoring/src/main/resources/dateformat.xsd)文件内容如下：
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsd:schema xmlns="http://www.gooalgene.com/schema/dateformat"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:beans="http://www.springframework.org/schema/beans"
        targetNamespace="http://www.gooalgene.com/schema/dateformat"
        elementFormDefault="qualified"
        attributeFormDefault="unqualified">

    <xsd:import namespace="http://www.springframework.org/schema/beans" />

    <xsd:element name="dateformat">
        <xsd:complexType>
            <xsd:complexContent>
                <!-- spring中该实例需要有一个唯一的id属性 -->
                <xsd:extension base="beans:identifiedType">
                    <xsd:attribute name="lenient" type="xsd:boolean" />
                    <xsd:attribute name="pattern" type="xsd:string" use="required" />
                </xsd:extension>
            </xsd:complexContent>
        </xsd:complexType>
    </xsd:element>
</xsd:schema>
```

#### 2.编写自己的`NamespaceHandler`文件
Spring官方文档是这样介绍`NamespaceHandler`的：
> Base interface used by the DefaultBeanDefinitionDocumentReader for handling custom namespaces in a Spring XML configuration file.
> Implementations are expected to return implementations of the BeanDefinitionParser interface for custom top-level tags and
> implementations of the BeanDefinitionDecorator interface for custom nested tags.

翻译一下即`NamespaceHandler`是一个底层接口用来处理spring xml中自定义的命名空间。如果xml中自定义配置是顶级标签，如下：
```
<beans xmlns="http://www.springframework.org/schema/beans" ...>
    <df:dateformat id="df" pattern="yyyy-MM-dd HH:mm" lenient="true" />
```
这里df这个namespace就是一个top-tag，那么对应的`NamespaceHandler`实现类就要注册一个`BeanDefinitionParser`，
如果xml中自定义标签在一个已知标签中作为一个属性存在，如下：
```
<bean id="checkingAccountService" class="com.foo.DefaultCheckingAccountService"
        jcache:cache-name="checking.account">
</bean>
```
上面`jcache`就是一个spring非spring内部属性，`NamespaceHandler`实现类应注册一个`BeanDefinitionDecorator`对象去解释这个属性。

[`NamespaceHandler`](/spring/xml-authoring/src/main/java/cn/crabime/xml/authoring/MyNamespaceHandler.java)实现如下：
```
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("dateformat", new SimpleDateFormatBeanDefinitionParser());
    }
}
```

#### 3.创建自己的`BeanDefinitionParser`解析spring xml中配置
spring肯定是无法解析我们自定义的标签，标签的解析还是交给开发者本身。上面代码中`SimpleDateFormatBeanDefinitionParser`就是我们解析
`SimpleDateFormat`类的解析器。正常解析器只需要实现`BeanDefinitionParser`即可。`BeanDefinitionParser`类spring文档如下：
> Interface used by the DefaultBeanDefinitionDocumentReader to handle custom, top-level (directly under <beans/>) tags.

spring通过`DefaultBeanDefinitionDocumentReader`调用该类去解析自定义顶级标签，这里文档中强调顶级指`<beans/>`标签内部的顶级标签，
并不是与`<beans/>`同级标签。
当前这里还是不建议直接实现`BeanDefinitionParser`去生成`BeanDefinition`，看了`BeanDefinition` N多的属性会让人晕厥，这里spring当然
也考虑到开发者感受，所以它自己做了一些抽象实现，把必要的实现留给开发者，只需要继承`AbstractSingleBeanDefinitionParser`即可。
[`SimpleDateFormatBeanDefinitionParser`](/spring/xml-authoring/src/main/java/cn/crabime/xml/authoring/SimpleDateFormatBeanDefinitionParser.java)代码如下：
```
public class SimpleDateFormatBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return SimpleDateFormat.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String pattern = element.getAttribute("pattern");
        builder.addConstructorArgValue(pattern);

        String lenient = element.getAttribute("lenient");
        if (StringUtils.hasText(lenient)) {
            builder.addPropertyValue("lenient", Boolean.valueOf(lenient));
        }
    }
}
```

#### 4.将上面那些组件注册到`spring`中
在resources目录下创建一个`META-INF`目录，在该目录下创建两个文件`spring.handlers`、`spring.schemas`。
**注意：创建的两个文件本身都是properties文件，里面出现的冒号(:)需要进行转义**
`spring.schemas`文件主要是让spring知道你写在xml文件中**schemaLocation**到底放在哪里，这里由于我把`dateformat.xsd`文件放在了resources目录下，也就是直接放在
classpath下，所以我的`spring.schemas`文件定义如下：
```
http\://www.gooalgene.com/schema/dateformat.xsd=dateformat.xsd
```
`spring.handlers`文件主要是让spring知道使用哪个`NamespaceHandler`进行命名空间解析。`spring.handlers`内容如下：
```
http\://www.gooalgene.com/schema/dateformat=cn.crabime.xml.authoring.MyNamespaceHandler
```

这里要注意，如果全程参考上面提供的spring官方文档会有一个小误会，[文档](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/xml-custom.html#extensible-xml-registration-spring-schemas)
中介绍xsd存放位置说法如下：
>  If you specify the mapping in this properties file, Spring will search for the schema on the classpath (in this case 'myns.xsd' in the 'org.springframework.samples.xml' package):
>  `http\://www.mycompany.com/schema/myns/myns.xsd=org/springframework/samples/xml/myns.xsd`

他的意思是放在`src/main/java`源码包中，这是有问题的，因为存放在包中，maven编译时是直接忽略源码包中非`.java`文件，作为依赖引入到其它项目中会包如下错误：
```
Exception in thread "main" org.springframework.beans.factory.xml.XmlBeanDefinitionStoreException: Line 9 in XML document from class path resource [authoring.xml] is invalid; nested exception is org.xml.sax.SAXParseException; lineNumber: 9; columnNumber: 72; cvc-complex-type.2.4.c: 通配符的匹配很全面, 但无法找到元素 'df:dateformat' 的声明。
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions(XmlBeanDefinitionReader.java:399)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:336)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:304)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:181)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:217)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:188)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:252)
	at org.springframework.context.support.AbstractXmlApplicationContext.loadBeanDefinitions(AbstractXmlApplicationContext.java:127)
	at org.springframework.context.support.AbstractXmlApplicationContext.loadBeanDefinitions(AbstractXmlApplicationContext.java:93)
	at org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory(AbstractRefreshableApplicationContext.java:129)
	at org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory(AbstractApplicationContext.java:614)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:515)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:139)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:83)
	at xmlauthoring.XmlAuthoringMain.main(XmlAuthoringMain.java:14)
Caused by: org.xml.sax.SAXParseException; lineNumber: 9; columnNumber: 72; cvc-complex-type.2.4.c: 通配符的匹配很全面, 但无法找到元素 'df:dateformat' 的声明。
	at com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper.createSAXParseException(ErrorHandlerWrapper.java:203)
	at com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper.error(ErrorHandlerWrapper.java:134)
	at com.sun.org.apache.xerces.internal.impl.XMLErrorReporter.reportError(XMLErrorReporter.java:396)
	at com.sun.org.apache.xerces.internal.impl.XMLErrorReporter.reportError(XMLErrorReporter.java:327)
	at com.sun.org.apache.xerces.internal.impl.XMLErrorReporter.reportError(XMLErrorReporter.java:284)
	at com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator$XSIErrorReporter.reportError(XMLSchemaValidator.java:452)
	at com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator.reportSchemaError(XMLSchemaValidator.java:3230)
	at com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator.handleStartElement(XMLSchemaValidator.java:1911)
	at com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator.emptyElement(XMLSchemaValidator.java:760)
	at com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl.scanStartElement(XMLNSDocumentScannerImpl.java:351)
	at com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl$FragmentContentDriver.next(XMLDocumentFragmentScannerImpl.java:2784)
	at com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl.next(XMLDocumentScannerImpl.java:602)
	at com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl.next(XMLNSDocumentScannerImpl.java:112)
	at com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl.scanDocument(XMLDocumentFragmentScannerImpl.java:505)
	at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:841)
	at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:770)
	at com.sun.org.apache.xerces.internal.parsers.XMLParser.parse(XMLParser.java:141)
	at com.sun.org.apache.xerces.internal.parsers.DOMParser.parse(DOMParser.java:243)
	at com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderImpl.parse(DocumentBuilderImpl.java:339)
	at org.springframework.beans.factory.xml.DefaultDocumentLoader.loadDocument(DefaultDocumentLoader.java:76)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadDocument(XmlBeanDefinitionReader.java:429)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions(XmlBeanDefinitionReader.java:391)
```

解决办法：将`dateformat.xsd`移到resources目录下即可。

至此，整个过程就结束了，测试代码参考[XmlAuthoringMain](/spring/spring-basic/src/main/java/xmlauthoring/XmlAuthoringMain.java)。
这里spring还给出了更为复杂的标签处理DMEO，如下面：
```
<df:component id="mc1" name="grandpa">
    <df:component name="father">
        <df:component name="boy" />
        <df:component name="girl" />
    </df:component>
    <df:component name="aunt" />
</df:component>
```
这种配置在spring-security中尤为常见。这里就不一一介绍了，感兴趣的同学可以参考`xml-authoring`模块，这里我已经做了一个完整实现即测试。