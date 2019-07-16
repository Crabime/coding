package depcheckxml;

import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * 每个年级都会有一个教导主任、很多学生、一个医务人员
 */
public class Grade {

    private Director director;

    private List<Student> students;

    private MedicalStuff medicalStuff;

    public Grade() {
    }

    public Grade(Director director, List<Student> students, MedicalStuff medicalStuff) {
        this.director = director;
        this.students = students;
        this.medicalStuff = medicalStuff;
    }

    public Director getDirector() {
        return director;
    }

    @Required
    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Required
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public MedicalStuff getMedicalStuff() {
        return medicalStuff;
    }

    public void setMedicalStuff(MedicalStuff medicalStuff) {
        this.medicalStuff = medicalStuff;
    }
}
