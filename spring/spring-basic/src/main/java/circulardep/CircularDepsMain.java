package circulardep;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularDepsMain {

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(CircularConfig.class);
            WoodpeckerNormal woodpecker = atx.getBean(WoodpeckerNormal.class);
            woodpecker.watchTree();
        } catch (Exception e) {
            // 这里打印完整异常堆栈
            e.printStackTrace();
        }
    }
}
