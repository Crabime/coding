package cn.crabime.springsupport;

import java.io.Serializable;

public class Order implements Serializable {

    private int id;

    private int personId;

    private String orderName;

    public Order() {
    }

    public Order(int personId, String orderName) {
        this.personId = personId;
        this.orderName = orderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("personId=").append(personId);
        sb.append(", orderName='").append(orderName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
