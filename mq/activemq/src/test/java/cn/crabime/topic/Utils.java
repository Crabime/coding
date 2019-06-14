package cn.crabime.topic;

import cn.crabime.springsupport.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    /**
     * 生成一个固定大小订单集合
     * @param size 集合大小
     * @return 指定大小数量的订单集合
     */
    protected static List<Order> orderGenerator(int size) {
        List<Order> result = new ArrayList<>();
        Order order = null;
        Random random = new Random();

        String[] dailyNecessities = {"牙膏", "牙刷", "毛巾", "卫生纸", "锅", "材", "米", "油", "盐", "老抽", "酱油", "生抽", "胡椒粉", "耗油"};
        for (int i = 0; i < size; i++) {
            String cur = dailyNecessities[(int) (random.nextFloat() * dailyNecessities.length)];
            order = new Order(1 + random.nextInt(3), cur);
            result.add(order);
        }
        return result;
    }
}
