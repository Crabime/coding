package cn.crabime.xml.authoring;

import org.springframework.beans.factory.FactoryBean;

import java.util.List;

public class ComplexComponentFactoryBean implements FactoryBean<ComplexComponent> {

    private ComplexComponent parent;

    private List<ComplexComponent> children;

    public void setParent(ComplexComponent parent) {
        this.parent = parent;
    }

    public void setChildren(List<ComplexComponent> children) {
        this.children = children;
    }

    @Override
    public ComplexComponent getObject() throws Exception {
        if (this.children != null && this.children.size() > 0) {
            for (ComplexComponent component : children) {
                this.parent.addComplexComponent(component);
            }
        }
        return parent;
    }

    @Override
    public Class<?> getObjectType() {
        return ComplexComponent.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
