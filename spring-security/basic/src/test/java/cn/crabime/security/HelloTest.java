package cn.crabime.security;

import cn.crabime.beans.User;
import cn.crabime.dao.UserDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by crabime on 5/30/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:applicationContext.xml"))
public class HelloTest extends TestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testFindAllUser() {
        List<User> users = userDao.findUserById(1);
        assertEquals(1, users.size());
    }
}
