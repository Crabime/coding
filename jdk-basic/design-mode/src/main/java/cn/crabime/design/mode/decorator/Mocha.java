package cn.crabime.design.mode.decorator;

public class Mocha extends CondimentDecorator {

    Beverage beverage;

    double myCost = 20;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",mocha";
    }

    @Override
    public double cost() {
        return myCost + beverage.cost();
    }
}
