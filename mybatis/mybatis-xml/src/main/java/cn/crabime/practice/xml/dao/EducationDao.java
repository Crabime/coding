package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Education;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EducationDao {

    boolean insertEducation(Education education);

    boolean insertCustomizeEducation(@Param("name") String name, @Param("education") Education education);

    int batchInsertEducation(List<Education> educationList);

    void truncateEducation();
}
