package cn.crabime.design.mode.chain.singleton;

public class ObjectFactoryNotThreadSafe {

    private static ObjectFactoryNotThreadSafe of;

    private ObjectFactoryNotThreadSafe() {

    }

    public static ObjectFactoryNotThreadSafe getInstance() {
        if (of == null) {
            of = new ObjectFactoryNotThreadSafe();
        }
        return of;
    }
}
