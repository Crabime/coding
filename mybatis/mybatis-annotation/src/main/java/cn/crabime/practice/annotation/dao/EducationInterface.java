package cn.crabime.practice.annotation.dao;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.mybatis.SimpleMapEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EducationInterface {

    @Insert("insert into education(type, charge, family_id, username) value(#{type}, #{charge}, #{familyId}, #{username})")
    void insertEducation(Education education);

    @Insert("insert into education(type, charge, username) value(#{type}, #{charge}, #{username})")
    void insertEducationWithoutFamilyId(Education education);

    @Select("select family_id as familyId, type from education")
    @ResultType(SimpleMapEntry.class)
    List<SimpleMapEntry> getAllMappings();

    @Select("select * from education where type = #{param1} and family_id = #{param2}")
    @ResultType(Education.class)
    List<Education> getByType(String type, int familyId);

    @Select("select * from education where type = #{param1} and username = #{param2} limit 1")
    @ResultType(Education.class)
    Education getByTypeAndUsername(String type, String username);

    @Update("truncate table education")
    void truncateEdu();
}