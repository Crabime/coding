package cn.crabime.xml.authoring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("dateformat", new SimpleDateFormatBeanDefinitionParser());
        registerBeanDefinitionParser("component", new ComplexComponentBeanDefinitionParser());
    }
}
