package cn.crabime.design.mode.chain.singleton;

public class ObjectThreadFactorySynchronizedBlock {

    private static ObjectThreadFactorySynchronizedBlock block;

    private ObjectThreadFactorySynchronizedBlock() {

    }

    public static ObjectThreadFactorySynchronizedBlock getInstance() {
        synchronized (ObjectThreadFactorySynchronizedBlock.class) {
            if (block == null) {
                block = new ObjectThreadFactorySynchronizedBlock();
            }
            return block;
        }
    }
}
