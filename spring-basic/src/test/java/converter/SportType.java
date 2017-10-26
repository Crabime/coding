package converter;

import java.util.Arrays;

/**
 * Created by crabime on 9/15/17.
 */
public class SportType {
    public static final SportType TENNIS = new SportType(1, "Tennis");
    public static final SportType SOCCER = new SportType(2, "Soccer");

    private int id;
    private String name;

    public SportType() {
    }

    public SportType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Iterable<SportType> list(){
        return Arrays.asList(TENNIS, SOCCER);
    }

    public static SportType getSport(int id){
        for(SportType sportType : list()){
            if(sportType.getId() == id){
                return sportType;
            }
        }
        return null;
    }

    public static SportType getTENNIS() {
        return TENNIS;
    }

    public static SportType getSOCCER() {
        return SOCCER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
