package cn.crabime.practice.mybatis;

public class Education {

    private int id;

    private int familyId;

    private String type;

    private double charge;

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
}
