package cn.crabime.practice.mybatis.dao;

import cn.crabime.practice.mybatis.Family;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyInterface {

    @Insert("insert into family(name, grade) value (#{name}, #{grade})")
    @Options(useGeneratedKeys = true)
    void insertFamily(Family family);

    @Insert("insert into family(name, grade) value (#{name}, #{grade})")
    void insertFamilyWithoutIdReturn(Family family);
}
