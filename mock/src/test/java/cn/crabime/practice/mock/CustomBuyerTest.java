package cn.crabime.practice.mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticClass.class)
public class CustomBuyerTest {

    private CustomBuyer buyer = new CustomBuyer();

    @Test
    public void testBuy() {
        mockStatic(StaticClass.class);
        when(StaticClass.generateUUID()).thenReturn("abcd");
        List<Order> orderList = buyer.buy(5);
        verifyStatic(times(5));
        StaticClass.generateUUID();
        Assert.assertEquals(5, orderList.size());
        Assert.assertEquals("abcd", orderList.get(0).getOrderId());
        verifyStatic(never());
        StaticClass.neverUsed();
    }

}