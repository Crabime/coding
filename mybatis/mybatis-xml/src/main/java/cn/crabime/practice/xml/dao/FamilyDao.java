package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Family;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FamilyDao {

    int insertFamily(Family family);

    /**
     * 更新了家庭计划和修改时间，但是不刷新缓存
     * @param name 家庭名
     * @param plan 家庭计划
     * @return 是否更新成功
     */
    boolean updateFamilyPlanByName(@Param("name") String name, @Param("plan") String plan);

    Family getFamilyByName(String name);

    List<Family> getFamily(String name, String familyPlan);

    void truncateFamily();
}
