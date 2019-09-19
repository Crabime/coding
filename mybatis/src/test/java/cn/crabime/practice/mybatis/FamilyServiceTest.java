package cn.crabime.practice.mybatis;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisAppMain.class)
public class FamilyServiceTest extends TestCase {

    @Autowired
    private FamilyService familyService;

    private Family family = new Family("Crabime", Grade.FATHER);;

    @Test(expected = RuntimeException.class)
    public void testInsertFamilyError() {

        doThrow(new RuntimeException()).when(familyService).insertFamily(family);
        familyService.insertFamily(family);
    }

    @Test
    public void testInsertFamilyWithEducationNormally() {
        Education education = new Education("english", 1200d);
        family.setEducation(education);
        familyService.insertFamily(family);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertFamilyWithEducationAbnormal() {
        Education education = new Education("english", 1200d);
        family.setEducation(education);
        doThrow(new RuntimeException()).when(familyService).insertFamily(family);
        familyService.insertFamily(family);
    }

    /**
     * familyService有事务，但是调用的educationService无事务，无事务方法在被调用期间也会套用当前FamilyService中已存在事务
     */
    @Test(expected = RuntimeException.class)
    public void testInsertFamilyWithNoneEducationAbnormal() {
        Education education = new Education("english", 1200d);
        family.setEducation(education);
        doThrow(new RuntimeException()).when(familyService).insertFamilyWithNonEducationTransaction(family);
        familyService.insertFamilyWithNonEducationTransaction(family);
    }

    /**
     * 使用事务传播级别为supports，如果存在事务则以事务方式执行，如果不存在则使用非事务方式执行
     */
    @Test(expected = RuntimeException.class)
    public void testInsertFamilyWithNoneEducationAbnormalUsingSupportsTransaction() {
        Education education = new Education("english", 1200d);
        family.setEducation(education);
        doThrow(new RuntimeException()).when(familyService).insertFamilyWithNonEducationTransaction(family);
        familyService.insertFamilyUsingSupportsPropagation(family);
    }
}
