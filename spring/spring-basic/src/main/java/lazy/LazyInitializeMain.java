package lazy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LazyInitializeMain {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext();
        atx.register(LazyAppConfig.class);
        atx.refresh();
        long end = System.currentTimeMillis();
        System.out.println("spring启动持续时间为：" + (end - start));

        Object obj = atx.getBean("obj");
        if (obj instanceof BeanSameNameNormalObject) {
            BeanSameNameNormalObject beanSameNameNormalObject = (BeanSameNameNormalObject) obj;
            beanSameNameNormalObject.introduce("杰瑞");
        }
    }
}
