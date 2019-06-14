package cn.crabime.db;

import cn.crabime.springsupport.Order;
import cn.crabime.utils.MyBatisDao;

@MyBatisDao
public interface OrderDao {

    int insertOneOrder(Order order);
}
