package lazy;

public class BeanSameNameNormalObject implements CommonAction {

    @Override
    public void introduce(String name) {
        System.out.println("我是一个新的bean，我的名字是：［" + name + "]");
    }
}
