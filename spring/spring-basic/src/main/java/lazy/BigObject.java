package lazy;

public class BigObject implements CommonAction {

    private String name;

    public BigObject(String name) {
        try {
            // 这里模拟启动时某些大对象的耗时操作
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.name = name;
    }


    @Override
    public void introduce(String name) {
        System.out.println("我的名字是：" + this.name + "，英文名字是：" + name);
    }
}
