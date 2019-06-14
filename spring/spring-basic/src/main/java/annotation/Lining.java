package annotation;

import org.springframework.stereotype.Component;

/**
 * Created by crabime on 9/17/17.
 */
@Component
public class Lining {
    private String material;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
