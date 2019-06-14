package cn.crabime;

import org.springframework.context.ApplicationEvent;

/**
 * Created by crabime on 11/11/16.
 */
public class EmailEvent extends ApplicationEvent {
    private String name;
    private String address;
    public EmailEvent(Object source) {
        super(source);
    }

    public EmailEvent(Object source, String name, String address) {
        super(source);
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
