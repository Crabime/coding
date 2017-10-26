package converter;

import converter.annotationutils.Habit;

import java.io.File;

/**
 * Created by crabime on 9/15/17.
 */
@Habit("Soccer")
public class Player {
    private String name;
    private String phone;
    private char gender;
    private char[] habits; //吃('E'), 喝('D'), 拉('P')
    private File icon; //使用Spring FileEditor进行解析

    public Player() {
    }

    public Player(String name, String phone, char gender, char[] habits) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.habits = habits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char[] getHabits() {
        return habits;
    }

    public void setHabits(char[] habits) {
        this.habits = habits;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }
}
