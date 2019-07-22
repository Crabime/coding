package circulardep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Woodpecker {

    @Autowired
    private Tree tree;

    private String name;

    public Woodpecker(String name) {
        this.name = name;
    }

    public void watchTree() {
        System.out.println("my name is ");
    }
}
