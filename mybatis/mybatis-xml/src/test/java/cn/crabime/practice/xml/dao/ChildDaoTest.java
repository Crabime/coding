package cn.crabime.practice.xml.dao;

import cn.crabime.practice.mybatis.Child;
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
public class ChildDaoTest {

    @Autowired
    private ChildDao childDao;

    @Test
    public void testInsertChildren() {
        List<Child> list = new ArrayList<>();
        Child child = new Child("小一", 11);
        child.setFamilyId(1);
        list.add(child);
        child = new Child("小二", 12);
        child.setFamilyId(2);
        list.add(child);
        child = new Child("小三", 13);
        child.setFamilyId(3);
        list.add(child);
        int r = childDao.insertChildren(list);
        assertEquals(3, r);
    }
}