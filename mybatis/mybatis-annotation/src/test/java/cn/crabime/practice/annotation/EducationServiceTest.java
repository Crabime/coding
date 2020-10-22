package cn.crabime.practice.annotation;

import cn.crabime.practice.mybatis.Education;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisAppConf.class)
public class EducationServiceTest extends TestCase {

    @Autowired
    private EducationService educationService;

    @Test
    public void testInsertAndFetchTwice() {
        Education education1 = new Education();
        education1.setType("English");
        education1.setCharge(1200d);
        education1.setFamilyId(12);
        education1.setUsername("张三");
        educationService.insertAndFetchTwice(education1);
    }
}