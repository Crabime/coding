package cn.crabime.practice.mock;

/**
 * 调用其它类，调用类会被mock出来
 */
public class MockClass {

    private CustomBuyer customBuyer;

    public void goingToMock(String str, int count) {
        str = new StringBuilder(str).reverse().toString();
        System.out.println("输入字符串反转后为:" + str);
        if (this.customBuyer != null) {
            customBuyer.buy(count);
        }
    }

    public void setCustomBuyer(CustomBuyer customBuyer) {
        this.customBuyer = customBuyer;
    }
}
