package cn.crabime.security.jackson;

/**
 * Created by crabime on 8/5/18.
 */
public class Cat extends AbstractAnimal {

    private double sleepingTime;

    public double getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(double sleepingTime) {
        this.sleepingTime = sleepingTime;
    }
}
