package cn.crabime.practice.mybatis;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("education")
public class Education implements Serializable {

    private int id;

    private int familyId;

    private String type;

    private double charge;

    private String username;

    public Education() {
    }

    public Education(String type, double charge) {
        this.type = type;
        this.charge = charge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
