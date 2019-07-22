package circulardep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Tree {

    @Autowired
    private Woodpecker woodpecker;

    private String treeName;

    public Tree(String treeName) {
        this.treeName = treeName;
    }

    public void treeName() {
        System.out.println("my name is " + treeName + ",I'm happy to be service by " + woodpecker);
    }
}
