package cn.crabime.practice.mock;

/**
 * 使用PowerMock去mock final方法
 */
public final class FinalUnderMockClass {

    public int say(String str) {
        System.out.println("他说：" + str);
        return str.length();
    }
}
