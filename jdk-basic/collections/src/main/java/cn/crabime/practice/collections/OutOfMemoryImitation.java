package cn.crabime.practice.collections;

/**
 * 模拟内存溢出场景
 * -Xmx1m -Xms1m -XX:+HeapDumpOnOutOfMemoryError
 */
public class OutOfMemoryImitation {

    // 创建一个4Mb字节数组
    private byte[] obj = new byte[1024 * 1024 * 10];

    public void addIntoObj() {
        System.out.println("程序是否还能正常工作？");
    }

    public static void main(String[] args) {
        OutOfMemoryImitation imitation = new OutOfMemoryImitation();
        imitation.addIntoObj();
    }
}
