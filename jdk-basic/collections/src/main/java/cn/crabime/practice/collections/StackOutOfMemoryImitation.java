package cn.crabime.practice.collections;

/**
 * 模拟栈中无法继续扩展内存时抛出的OutOfMemoryError问题，这里只能得到StackOverflowError错误
 * -Xss128k
 */
public class StackOutOfMemoryImitation {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackOutOfMemoryImitation imitation = new StackOutOfMemoryImitation();
        try {
            imitation.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length : " + imitation.stackLength);
            e.printStackTrace();
        }

    }
}
