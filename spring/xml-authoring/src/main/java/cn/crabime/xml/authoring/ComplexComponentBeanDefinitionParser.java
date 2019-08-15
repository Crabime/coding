package cn.crabime.xml.authoring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

public class ComplexComponentBeanDefinitionParser extends AbstractBeanDefinitionParser {
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        return parseComplexComponentElement(element);
    }

    private AbstractBeanDefinition parseComplexComponentElement(Element element) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ComplexComponentFactoryBean.class);
        factory.addPropertyValue("parent", parseComplexComponent(element));
        List<Element> childElements = DomUtils.getChildElementsByTagName(element, "component");
        if (childElements.size() > 0) {
            parseChildComplexComponent(childElements, factory);
        }
        return factory.getBeanDefinition();
    }

    /**
     * 创建parentBeanDefinition
     */
    private BeanDefinition parseComplexComponent(Element element) {
        BeanDefinitionBuilder complexComponentDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ComplexComponent.class);
        complexComponentDefinitionBuilder.addPropertyValue("name", element.getAttribute("name"));
        return complexComponentDefinitionBuilder.getBeanDefinition();
    }

    private void parseChildComplexComponent(List<Element> elements, BeanDefinitionBuilder factory) {
        ManagedList<BeanDefinition> managedList = new ManagedList<>(elements.size());
        for (Element element : elements) {
            managedList.add(parseComplexComponent(element));
        }
        factory.addPropertyValue("children", managedList);
    }
}
