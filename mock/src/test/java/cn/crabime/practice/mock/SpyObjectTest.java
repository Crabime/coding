package cn.crabime.practice.mock;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpyObjectTest {

    @Test
    public void testMethodC() {
        SpyObject spyObject = new SpyObject();
        SpyObject spy = Mockito.spy(spyObject);
        doNothing().when(spy).methodA();
        spy.methodC();

        // 判断spy是否为一个mock对象OR spy对象
        assertTrue(Mockito.mockingDetails(spy).isMock());
    }

    @Test
    public void testSpyObjectAndTimeConsumedMethodCalling() {
        SpyObject spyObject = new SpyObject();
        SpyCalcProcedure procedure = new SpyCalcProcedure();
        SpyCalcProcedure spyProcedure = spy(procedure);
        spyObject.setProcedure(spyProcedure);
        when(spyProcedure.calcProcess(anyInt())).thenReturn("spy result");
        spyObject.testCallTimeConsumedMethod(10);
    }
}