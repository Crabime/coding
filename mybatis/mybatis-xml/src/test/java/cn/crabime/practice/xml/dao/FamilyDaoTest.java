package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Family;
import cn.crabime.practice.mybatis.Grade;
import cn.crabime.practice.xml.MybatisXmlConf;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisXmlConf.class)
public class FamilyDaoTest {

    @Autowired
    private FamilyDao familyDao;

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



    @After
    public void tearDown() {
        familyDao.truncateFamily();
    }
}