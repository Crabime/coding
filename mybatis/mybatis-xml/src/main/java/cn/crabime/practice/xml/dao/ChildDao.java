package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Child;

import java.util.List;

public interface ChildDao {

    /**
     * mybatis 3.5.5支持方法只有单个参数时，使用具体参数名
     * @param childList 待插入列表
     * @return 插入行数
     */
    int insertChildren(List<Child> childList);
}
