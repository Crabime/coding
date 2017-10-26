package cn.crabime;

/**
 * Created by crabime on 11/10/16.
 */
public class Teacher {
    private Student student;

    public void setStudent(Student student){
        this.student = student;
    }

    public void check(){
        student.say();
        System.out.println("Teacher check students number");
    }
}
