package cn.crabime.design.mode.decorator;

abstract class Beverage {

    protected String descriptionString = "未知饮料";

    public String getDescription() {
        return descriptionString;
    }

    /**
     * 计算饮料价格
     */
    public abstract double cost();
}
