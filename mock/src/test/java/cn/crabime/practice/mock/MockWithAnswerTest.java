package cn.crabime.practice.mock;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;

public class MockWithAnswerTest {

    /**
     * 无任何mock情况
     */
    @Test
    public void testBuySomething() {
        MockWithAnswer instance = new MockWithAnswer();
        CustomBuyer buyer = new CustomBuyer();
        instance.setBuyer(buyer);
        instance.buySomething(20);
    }

    /**
     * 如果只是mock不给answer，mock对象的方法也会被执行，但是返回的都是默认值
     */
    @Test
    public void testBuySomethingMockingCustomBuyer() {
        MockWithAnswer instance = new MockWithAnswer();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class);
        instance.setBuyer(mockCustomBuyer);
        instance.buySomething(20);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).buy(20);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).pay(0d);
    }

    /**
     * mock的对象部分方法执行的就是实际方法，部分方法仍然为mock结果
     */
    @Test
    public void testBuySomethingWithAnswer() {
        MockWithAnswer instance = new MockWithAnswer();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class, new CallsRealMethods());
        Mockito.when(mockCustomBuyer.pay(anyDouble())).thenReturn(true);
        instance.setBuyer(mockCustomBuyer);
        instance.buySomething(20);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).pay(anyDouble());
    }

    /**
     * 使用ReturnsEmptyValues针对某个mock方法返回该种返回类型的原始类型
     */
    @Test
    public void testBuySomethingWithDefaultReturn() {
        MockWithAnswer instance = new MockWithAnswer();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class);
        // 返回对应数据类型默认值
        Mockito.when(mockCustomBuyer.buy(anyInt())).then(new ReturnsEmptyValues());
        instance.setBuyer(mockCustomBuyer);
        instance.buySomething(20);
        // 由于前面返回的是一个空集合，也就是买货总量为0，这里需要付的钱也为0
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).pay(0);
        Mockito.verify(mockCustomBuyer, Mockito.never()).pay(1);
    }

    /**
     * 如果某个mock行为都verify后，最后再调用verifyNoMoreInteractions即表示无任何新的交互
     */
    @Test
    public void testBuySomethingVerifyNoMoreInteractions() {
        MockWithAnswer instance = new MockWithAnswer();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class);
        Mockito.when(mockCustomBuyer.buy(anyInt())).then(new ReturnsEmptyValues());
        instance.setBuyer(mockCustomBuyer);
        instance.buySomething(20);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).buy(20);
        Mockito.verify(mockCustomBuyer, Mockito.never()).buy(1);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).pay(0);
        Mockito.verifyNoMoreInteractions(mockCustomBuyer);
    }
}