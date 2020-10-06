package cn.crabime.practice.annotation.dao;

import cn.crabime.practice.mybatis.Family;
import cn.crabime.practice.mybatis.handler.AutoEnumTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FamilyInterface {

    @Insert("insert into family(name, grade, education_id) value (#{name}, #{grade}, #{education.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFamily(Family family);

    @Insert("insert into family(name, grade) value (#{name}, #{grade})")
    void insertFamilyWithoutIdReturn(Family family);

    @Update("truncate table family")
    void truncateFamily();

    @Select("select * from family where id = #{arg0}")
    @Results(id = "familyMap",
            value = {
                @Result(property = "name", column = "name"),
                @Result(property = "grade", column = "grade", typeHandler = AutoEnumTypeHandler.class),
                    @Result(property = "education", column = "education_id",
                            one = @One(select = "cn.crabime.practice.annotation.dao.EducationInterface.getById"))
            })
    Family getById(int id);

    @Select("select * from family where name = #{arg0}")
    @ResultMap("familyMap")
    List<Family> getByName(String name);

    @Select("select * from family where name = #{arg0}")
    Family getFamilyByNameUsingAutoMapping(String name);

    @Select("select count(*) from family where name = #{arg0}")
    boolean isFamilyExists(String familyName);
}
