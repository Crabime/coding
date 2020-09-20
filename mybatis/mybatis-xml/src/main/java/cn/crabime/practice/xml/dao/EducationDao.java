package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Education;

public interface EducationDao {

    boolean insertEducation(Education education);

    void truncateEducation();
}
