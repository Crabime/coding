package circulardep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TreeNormal {

    @Autowired
    private WoodpeckerNormal woodpecker;

    @Value("${tree.name}")
    private String treeName;

    public void treeName() {
        System.out.println("my name is " + treeName + ",I'm happy to be service by " + woodpecker.getName());
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
}
