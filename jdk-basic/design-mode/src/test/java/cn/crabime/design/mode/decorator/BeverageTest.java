package cn.crabime.design.mode.decorator;

import junit.framework.TestCase;
import org.junit.Test;

public class BeverageTest extends TestCase {

    private Beverage beverage;

    @Override
    protected void setUp() throws Exception {
        beverage = new HouseBlend();
    }

    @Test
    public void testInit() {
        assertEquals("House Blend Coffee", beverage.getDescription());
        assertEquals(15.0, beverage.cost());
    }

    @Test
    public void testAddMochaInBeverage() {
        beverage = new HouseBlend();
        Beverage mocha = new Mocha(beverage);
        assertEquals(35.0, mocha.cost());
        assertEquals("House Blend Coffee,mocha", mocha.getDescription());
    }
}