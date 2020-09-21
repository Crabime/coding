package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Child;
import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.mybatis.Family;
import cn.crabime.practice.mybatis.Grade;
import cn.crabime.practice.xml.MybatisXmlConf;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisXmlConf.class)
public class FamilyDaoTest {

    @Autowired
    private FamilyDao familyDao;

    @Autowired
    private EducationDao educationDao;

    @Autowired
    private ChildDao childDao;

    @Test
    public void testInsertFamilyNormally() {
        Family family = new Family();
        family.setName("张三");
        family.setGrade(Grade.CHILD);
        family.setFamilyPlan("出去旅行");
        familyDao.insertFamily(family);

        Family f = familyDao.getFamilyByName("张三");
        assertEquals("出去旅行", f.getFamilyPlan());
    }

    @Test
    public void testDynamicSql() {
        Family family = new Family();
        family.setName("李四");
        family.setGrade(Grade.FATHER);
        family.setFamilyPlan("出去旅行");
        familyDao.insertFamily(family);
        List<Family> t = familyDao.getFamily("李四", "旅行");
        assertEquals(1, t.size());
        // 走缓存
        t = familyDao.getFamily("李四", "旅行");
        assertEquals(1, t.size());

        boolean result = familyDao.updateFamilyPlanByName("李四", "去游乐场");
        assertTrue(result);

        // 更新了后仍走缓存
        t = familyDao.getFamily("李四", "旅行");
        assertEquals(1, t.size());
    }

    @Test
    public void insertFamilyWithEducation() {
        Family family = new Family("Crabime", Grade.FATHER);
        family.setFamilyPlan("旅行");
        familyDao.insertFamily(family);

        Education education = new Education("english", 1200d);
        education.setUsername("我是谁");
        education.setFamilyId(family.getId());
        educationDao.insertEducation(education);

        Family newFamily = familyDao.getFamilyByName("Crabime");
        assertEquals("我是谁", newFamily.getEducation().getUsername());
    }

    @Test
    public void testSearchWithCollectionTag() {
        Family family = new Family("Crabime", Grade.FATHER);
        family.setFamilyPlan("烹饪");
        familyDao.insertFamily(family);

        List<Child> list = new ArrayList<>();
        Child child = new Child("小一", 11);
        child.setFamilyId(family.getId());
        list.add(child);
        child = new Child("小二", 12);
        child.setFamilyId(family.getId());
        list.add(child);
        child = new Child("小三", 13);
        child.setFamilyId(family.getId());
        list.add(child);
        childDao.insertChildren(list);


        Family realFamily = familyDao.getFamilyAndChildList("Crabime");
        assertEquals(3, realFamily.getChildList().size());
    }

    // @After
    public void tearDown() {
        familyDao.truncateFamily();
        educationDao.truncateEducation();
    }
}