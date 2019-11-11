package cn.crabime.design.mode.chain.singleton;

/**
 * 推荐使用
 */
public class ObjectThreadFactoryDoubleCheckSynchronizedBlock {

    private static ObjectThreadFactoryDoubleCheckSynchronizedBlock block;

    private ObjectThreadFactoryDoubleCheckSynchronizedBlock() {}

    public static ObjectThreadFactoryDoubleCheckSynchronizedBlock getInstance() {
        if (block == null) {
            synchronized (ObjectThreadFactoryDoubleCheckSynchronizedBlock.class) {
                if (block == null) {
                    block = new ObjectThreadFactoryDoubleCheckSynchronizedBlock();
                }
            }
        }
        return block;
    }
}
