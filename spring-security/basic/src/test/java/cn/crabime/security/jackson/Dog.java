package cn.crabime.security.jackson;

/**
 * Created by crabime on 8/5/18.
 */
public class Dog extends AbstractAnimal {
    private String loyalty;

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }
}
