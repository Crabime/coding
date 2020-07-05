package cn.crabime.practice.mybatis;

import cn.crabime.practice.mybatis.vo.SimpleMapEntry;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisAppMain.class)
public class MybatisAppTest {

    @Autowired
    private EducationService educationService;

    @Test
    public void testInsertEducation() {
        Assert.assertNotNull(educationService);
        Education education = new Education();
        education.setFamilyId(2);
        education.setCharge(3.2);
        education.setType("教学一");
        educationService.insertOneEducation(education);
    }

    @Test
    public void testImmutablePair() {
        List<SimpleMapEntry> allMappings = educationService.getAllMappings();
        Assert.assertEquals(2, allMappings.size());
    }
}
