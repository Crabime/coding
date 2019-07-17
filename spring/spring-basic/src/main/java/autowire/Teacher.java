package autowire;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class Teacher {

    private Student student;

    @Inject
    public Teacher(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
