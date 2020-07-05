package cn.crabime.practice.mock;

/**
 * 被mock的第一个对象
 */
public class InOrderFirst {

    public void calledFirst() {
        System.out.println("我是第一个被调用的");
    }
}
