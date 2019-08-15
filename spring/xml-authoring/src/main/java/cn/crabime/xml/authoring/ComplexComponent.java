package cn.crabime.xml.authoring;

import java.util.ArrayList;
import java.util.List;

public class ComplexComponent {

    private String name;

    private List<ComplexComponent> list = new ArrayList<>();

    public void addComplexComponent(ComplexComponent complexComponent) {
        this.list.add(complexComponent);
    }

    public List<ComplexComponent> getComplexComponents() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
