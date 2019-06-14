package cn.crabime.security.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by crabime on 8/5/18.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
abstract class AbstractAnimal {

    private String type;

    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
