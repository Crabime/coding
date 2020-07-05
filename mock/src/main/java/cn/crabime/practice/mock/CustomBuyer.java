package cn.crabime.practice.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 普通买家购买商品时会生成一个UUID订单号
 */
public class CustomBuyer {

    public List<Order> buy(int num) {
        List<Order> result = new ArrayList<>();
        Order order = null;
        for (int i = 0; i < num; i++) {
            order = new Order();
            order.setId(i);
            String orderId = StaticClass.generateUUID();
            order.setOrderId(orderId);
            int amount = new Random().nextInt(30);
            order.setAmount(amount);
            System.out.println("第" + i + "次遍历，价格为:" + amount);
            result.add(order);
        }
        return result;
    }

    public boolean pay(double amount) {
        long round = Math.round(amount);
        boolean result = round > 100L;
        if (result) {
            System.out.println("超过100元免配送费");
        } else {
            System.out.println("未满100元，无法免配送费");
        }
        return result;
    }
}
