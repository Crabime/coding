package depcheckxml;

/**
 * 学生
 */
public class Student {

    private String name;

    private String habit;

    public Student(String name, String habit) {
        this.name = name;
        this.habit = habit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }
}
