package cn.crabime.felix.service;

public class SimplePerson {
    private String name;
    private int age;

    public SimplePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SimplePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
