package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.xml.MybatisXmlConf;
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
public class EducationDaoTest {

    @Autowired
    private EducationDao educationDao;

    @Test
    public void testInsertCustomizeEducation() {
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setFamilyId(12);
        educationDao.insertCustomizeEducation("张三", education1);
    }

    @Test
    public void testBatchInsertEducationWithGeneratedKeys() {
        List<Education> educationList = new ArrayList<>(8);
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setUsername("张三");
        educationList.add(education1);
        Education education2 = new Education();
        education2.setType("Math");
        education2.setCharge(800d);
        education2.setUsername("李四");
        educationList.add(education2);

        int result = educationDao.batchInsertEducation(educationList);
        assertEquals(2, result);

        assertNotEquals(0, education1.getId());
        assertNotEquals(0, education2.getId());
    }
}