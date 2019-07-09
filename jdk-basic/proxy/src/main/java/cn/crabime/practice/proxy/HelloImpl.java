package cn.crabime.practice.proxy;

public class HelloImpl implements Hello {

    public HelloImpl() {
    }

    @Override
    public void sayHello(String hello) {
        System.out.println("来到hello方法中" + hello);
    }
}
