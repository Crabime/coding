package circulardep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Woodpecker {

    private Tree tree;

    @Autowired
    public Woodpecker(Tree tree) {
        this.tree = tree;
    }

    @Value("${woodpecker.name}")
    private String name;

    public void watchTree() {
        System.out.println("my name is " + name + ", tree name is " + tree.getTreeName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
