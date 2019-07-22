package circulardep;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularDepsMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext("circulardep");
        Woodpecker woodpecker = atx.getBean(Woodpecker.class);
    }
}
