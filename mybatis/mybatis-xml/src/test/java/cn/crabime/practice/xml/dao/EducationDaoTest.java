package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.xml.MybatisXmlConf;
import cn.crabime.practice.xml.vo.EducationRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisXmlConf.class)
public class EducationDaoTest {

    private final static Logger logger = Logger.getLogger(EducationDaoTest.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EducationDao educationDao;

    @Test
    public void testInsertCustomizeEducation() {
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setFamilyId(12);
        String username = "张三";
        educationDao.insertCustomizeEducation(username, education1);
        logger.info("username=" + username + ",education_id=" + education1.getId() + ",education.username=" + education1.getUsername());
    }

    @Test
    public void testInsertEducationWithMillionSecond() {
        Date date = new Date(1603897011891L);
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setFamilyId(12);
        education1.setGmtCreate(date);
        boolean res = educationDao.insertEducation(education1);
        assertTrue(res);

        // DB中Education对象，时间戳会发生变化
        Education eduRc = educationDao.findById(education1.getId());
        Date dateAfterTruncate = DateUtils.truncate(date, Calendar.SECOND);
        // Date secondPlus = DateUtils.addSeconds(dateAfterTruncate, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String expect = dateFormat.format(secondPlus);
        String expect = dateFormat.format(dateAfterTruncate);
        assertEquals(expect, dateFormat.format(eduRc.getGmtCreate()));


    }

    @Test
    public void testClassForName() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("cn.crabime.practice.mybatis.handler.TruncDateTypeHandler");
        assertNotNull(aClass);
    }

    @Test
    public void testMybatisLevel1Cache() {
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setFamilyId(12);
        education1.setUsername("张三");
        boolean result = educationDao.insertEducation(education1);
        assertTrue(result);

        int id = education1.getId();
        Education edu = educationDao.findById(id);
        assertEquals("English", edu.getType());
        edu = educationDao.findById(id);
        assertEquals(1200d, edu.getCharge(), 0);
    }

    @Test
    public void testMybatisOgnl() {
        EducationRequest request = new EducationRequest();
        request.setType("Biology");
        request.setLevel(1000);
        List<Education> educationList = educationDao.findByEdu(request);
        assertNotNull(educationList);
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
        logger.info("id1=" + education1.getId());
        logger.info("id2=" + education2.getId());
    }
}