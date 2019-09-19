package cn.crabime.practice.mybatis.dao;

import cn.crabime.practice.mybatis.Education;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationInterface {

    @Insert("insert into education(type, charge, family_id) value(#{type}, #{charge}, #{familyId})")
    void insertEducation(Education education);

    @Insert("insert into education(type, charge) value(#{type}, #{charge})")
    void insertEducationWithoutFamilyId(Education education);
}
