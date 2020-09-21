package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Child;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChildDao {

    int insertChildren(@Param("childList") List<Child> childList);
}
