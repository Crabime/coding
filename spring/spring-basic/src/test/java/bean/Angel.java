package bean;

/**
 * Created by crabime on 9/7/17.
 */
public class Angel {
    private String alias;
    public Angel() {
    }

    public Angel(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    protected void fly(){
        System.out.println("fly on the air");
    }

    private String say(String greeting){
        return "hello" + greeting;
    }

    @Override
    public String toString() {
        return "Angel";
    }
}
