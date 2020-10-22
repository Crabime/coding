package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.xml.vo.EducationRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EducationDao {

    Education findById(@Param("id") int id);

    boolean insertEducation(Education education);

    boolean insertCustomizeEducation(@Param("name") String name, @Param("education") Education education);

    int batchInsertEducation(List<Education> educationList);

    List<Education> findByEdu(@Param("req") EducationRequest request);

    void truncateEducation();
}
