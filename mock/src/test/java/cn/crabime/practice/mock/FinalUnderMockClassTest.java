package cn.crabime.practice.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FinalUnderMockClass.class)
public class FinalUnderMockClassTest {

    @Test
    public void testSay() {
        FinalUnderMockClass spyMockClass = PowerMockito.spy(new FinalUnderMockClass());
        Mockito.when(spyMockClass.say(anyString())).thenReturn(5);
        int len = spyMockClass.say("hello");
        Mockito.verify(spyMockClass, Mockito.times(1)).say(anyString());
        assertEquals(5, len);
    }
}