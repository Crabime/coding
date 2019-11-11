package cn.crabime.design.mode.chain.singleton;

/**
 * 加排它锁，效率低
 */
public class ObjectThreadFactorySynchronized {

    private static ObjectThreadFactorySynchronized of;

    private ObjectThreadFactorySynchronized() {

    }

    public synchronized static ObjectThreadFactorySynchronized getInstance() {
        if (of == null) {
            of = new ObjectThreadFactorySynchronized();
        }
        return of;
    }
}
