package cn.crabime.practice.mybatis.dao;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.mybatis.vo.SimpleMapEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationInterface {

    @Insert("insert into education(type, charge, family_id) value(#{type}, #{charge}, #{familyId})")
    void insertEducation(Education education);

    @Insert("insert into education(type, charge) value(#{type}, #{charge})")
    void insertEducationWithoutFamilyId(Education education);

    @Select("select family_id as familyId, type from education")
    @ResultType(SimpleMapEntry.class)
    List<SimpleMapEntry> getAllMappings();

    @Select("select * from education where type = #{param1} and family_id = #{param2}")
    @ResultType(Education.class)
    List<Education> getByType(String type, int familyId);
}
