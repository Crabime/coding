package method.override;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LookUpMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(LookUpConfig.class);
        DoorKeeper doorKeeper = atx.getBean(DoorKeeper.class);
        System.out.println("===从正常人那里问一下时间===");
        doorKeeper.showMeTime(BeanType.PROTOTYPE);
        System.out.println("===再次获取时间===");
        Thread.sleep(3000);
        doorKeeper.showMeTime(BeanType.PROTOTYPE);

        System.out.println("\n\n===从懒人那里问一下时间===");
        doorKeeper.showMeTime(BeanType.SINGLETON);
        System.out.println("===再次获取时间===");
        Thread.sleep(3000);
        doorKeeper.showMeTime(BeanType.SINGLETON);
    }
}
