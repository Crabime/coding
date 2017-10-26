package cn.crabime;

import org.junit.Test;

/**
 * Created by crabime on 6/20/17.
 * 单类例为什么需要在Spring容器中初始化呢?如何初始化静态工厂类?
 */
public class SingletonTest {
    @Test
    public void testSingleton(){
        SingletonBean singletonBean = SingletonBean.newInstance();
        singletonBean.setName("Zuckberge");
        System.out.println(singletonBean);
    }
}
