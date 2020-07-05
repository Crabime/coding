package cn.crabime.practice.mock;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class InOrderMockClassTest {

    @Test
    public void testMain() {
        InOrderMockClass instance = new InOrderMockClass();
        InOrderFirst inOrderFirst = Mockito.mock(InOrderFirst.class);
        InOrderSecond inOrderSecond = Mockito.mock(InOrderSecond.class);
        instance.setInOrderFirst(inOrderFirst);
        instance.setInOrderSecond(inOrderSecond);
        // 对void类型方法进行mock
        Mockito.doCallRealMethod().when(inOrderFirst).calledFirst();
        Mockito.doCallRealMethod().when(inOrderSecond).callSecond();
        instance.main();
        InOrder inOrder = Mockito.inOrder(inOrderFirst, inOrderSecond);
        inOrder.verify(inOrderFirst).calledFirst();
        inOrder.verify(inOrderSecond).callSecond();
    }

    @Test
    public void testCallWithoutOrder() {
        InOrderMockClass instance = new InOrderMockClass();
        InOrderFirst inOrderFirst = Mockito.mock(InOrderFirst.class);
        InOrderSecond inOrderSecond = Mockito.mock(InOrderSecond.class);
        instance.setInOrderFirst(inOrderFirst);
        instance.setInOrderSecond(inOrderSecond);
        Mockito.doCallRealMethod().when(inOrderFirst).calledFirst();
        Mockito.doCallRealMethod().when(inOrderSecond).callSecond();
        instance.callInOrder(false);
        // 这里的inOrder其实mockito并不关注，它只关注实际方法体内的执行方式
        InOrder inOrder = Mockito.inOrder(inOrderFirst, inOrderSecond);
        inOrder.verify(inOrderSecond).callSecond();
        inOrder.verify(inOrderFirst).calledFirst();
    }

    @Test
    public void testVerifyNoMorAction() {

    }
}