package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Restaurant;
import cn.crabime.practice.xml.MybatisXmlConf;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisXmlConf.class)
public class RestaurantDaoTest {

    @Autowired
    private RestaurantDao restaurantDao;

    @Test
    public void testAddOneRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("湖北第一肥肠粉");
        boolean r = restaurantDao.registerRestaurant(restaurant);
        assertTrue(r);
    }

    @Test
    public void testGetRestaurantByContext() {
        restaurantDao.getRestaurantByContext(3, new ResultHandler<Restaurant>() {
            @Override
            public void handleResult(ResultContext<? extends Restaurant> resultContext) {
                Restaurant restaurant = resultContext.getResultObject();
                System.out.println(JSON.toJSONString(restaurant));
                if (restaurant.getId() > 7) {
                    resultContext.stop();
                }
            }
        });
    }
}
