package cn.crabime.design.mode.decorator;

public class HouseBlend extends Beverage {

    public HouseBlend() {
        descriptionString = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 15;
    }
}
