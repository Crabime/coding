package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Restaurant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

public interface RestaurantDao {

    boolean registerRestaurant(Restaurant restaurant);

    void getRestaurantByContext(@Param("bound") int bound, ResultHandler<Restaurant> resultHandler);
}
