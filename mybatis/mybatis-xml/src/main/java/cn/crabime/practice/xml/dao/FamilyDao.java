package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Family;

public interface FamilyDao {

    int insertFamily(Family family);

    Family getFamilyByName(String name);

    void truncateFamily();
}
