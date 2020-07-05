package cn.crabime.practice.mock;

import java.util.List;

/**
 * 使用mockito的answer
 */
public class MockWithAnswer {

    private CustomBuyer buyer;

    public void buySomething(int count) {
        System.out.println("买点什么东西回去吧");
        if (buyer != null) {
            List<Order> orders = buyer.buy(count);
            // 计算本次订单总价格判断是否应该免配送
            long totalAmount = orders.stream().mapToInt(Order::getAmount).sum();
            boolean free = buyer.pay(totalAmount);
            System.out.println("最终消费" + totalAmount + "，是否免单:" + free);
        }
    }

    public void setBuyer(CustomBuyer buyer) {
        this.buyer = buyer;
    }
}
