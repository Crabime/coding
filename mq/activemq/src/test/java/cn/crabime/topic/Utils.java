package cn.crabime.topic;

import cn.crabime.springsupport.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    /**
     * ����һ���̶���С��������
     * @param size ���ϴ�С
     * @return ָ����С�����Ķ�������
     */
    protected static List<Order> orderGenerator(int size) {
        List<Order> result = new ArrayList<>();
        Order order = null;
        Random random = new Random();

        String[] dailyNecessities = {"����", "��ˢ", "ë��", "����ֽ", "��", "��", "��", "��", "��", "�ϳ�", "����", "����", "������", "����"};
        for (int i = 0; i < size; i++) {
            String cur = dailyNecessities[(int) (random.nextFloat() * dailyNecessities.length)];
            order = new Order(1 + random.nextInt(3), cur);
            result.add(order);
        }
        return result;
    }
}
