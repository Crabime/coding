package cn.crabime.db;

import cn.crabime.springsupport.Order;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:beans.xml"))
public class OrderTest extends TestCase {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testInsertOneOrder() {
        Order order = new Order(1, "¿¾Ïä");
        Integer resultId = orderDao.insertOneOrder(order);
        assertNotNull(resultId);
    }
}
