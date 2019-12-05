package cn.crabime.design.mode.decorator;

public class Whip extends CondimentDecorator {

    Beverage beverage;

    double myCost = 10;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",whip";
    }

    @Override
    public double cost() {
        return myCost + beverage.cost();
    }
}
