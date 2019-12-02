package cn.crabime.practice.mybatis.perf;

import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface TeacherMapper {

    @Insert("insert into teacher(name, age, nature) values(#{name}, #{age}, #{nature})")
    void insertTeacher(Teacher teacher);

    void batchInsertTeacher(List<Teacher> teacherList);
}
