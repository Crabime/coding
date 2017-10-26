package jfinal.entity;

/**
 * Created by crabime on 7/7/17.
 */
public class Address {
    private Long id;
    private Long member_id;
    private String des;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", member_id=" + member_id +
                ", des='" + des + '\'' +
                '}';
    }
}
