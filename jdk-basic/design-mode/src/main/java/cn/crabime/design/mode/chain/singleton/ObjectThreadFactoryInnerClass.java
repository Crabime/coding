package cn.crabime.design.mode.chain.singleton;

public class ObjectThreadFactoryInnerClass {

    private static class SingleInstance {

        private static ObjectThreadFactoryInnerClass objectFactory = new ObjectThreadFactoryInnerClass();
    }

    /**
     * 由jvm协助我们保证获取单例类的线程安全性
     */
    public static ObjectThreadFactoryInnerClass getInstance() {
        return SingleInstance.objectFactory;
    }
}
