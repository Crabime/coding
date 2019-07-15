package lazy;

public class NormalObject implements CommonAction {

    private String name;

    private int age;

    public NormalObject(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    public void introduce(String name) {
        System.out.println("我叫：" + this.name + "英文名字是： " + name + "，今年" + age + "岁");
    }
}
