package circulardep;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularDepsMain {

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext("circulardep");
            Woodpecker woodpecker = atx.getBean(Woodpecker.class);
            woodpecker.watchTree();
        } catch (Exception e) {
            // 这里打印完整异常堆栈
            e.printStackTrace();
        }
    }
}
