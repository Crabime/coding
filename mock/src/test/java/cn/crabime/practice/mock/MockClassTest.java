package cn.crabime.practice.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;

public class MockClassTest {

    @Test
    public void testGoingToMock() {
        MockClass instance = new MockClass();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class);
        instance.setCustomBuyer(mockCustomBuyer);
        List<Order> orderList = new ArrayList<>();
        Mockito.stub(mockCustomBuyer.buy(anyInt())).toReturn(orderList);
        instance.goingToMock("hello", 5);
        Mockito.verify(mockCustomBuyer, Mockito.times(1)).buy(5);
        Mockito.verify(mockCustomBuyer, Mockito.never()).buy(3);
    }

    @Test
    public void testGoingToMockUsingWhen() {
        MockClass instance = new MockClass();
        CustomBuyer mockCustomBuyer = Mockito.mock(CustomBuyer.class);
        instance.setCustomBuyer(mockCustomBuyer);
        List<Order> orderList = new ArrayList<>();
        Mockito.when(mockCustomBuyer.buy(anyInt())).thenReturn(orderList);
        instance.goingToMock("hello", 5);
        Mockito.verify(mockCustomBuyer, Mockito.never()).buy(3);
    }
}