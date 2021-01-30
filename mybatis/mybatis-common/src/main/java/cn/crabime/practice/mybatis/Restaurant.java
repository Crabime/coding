package cn.crabime.practice.mybatis;

public class Restaurant {

    private long id;

    private Integer touristNum;

    private String name;

    private Boolean open;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTouristNum() {
        return touristNum;
    }

    public void setTouristNum(Integer touristNum) {
        this.touristNum = touristNum;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
