package com.gooalgene.service;

import com.gooalgene.beans.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by crabime on 11/2/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(locations = "classpath:applicationContext.xml"))
public class UserServiceTest extends TestCase {
    @Autowired
    private UserService userService;

    @Test
    public void testUserService(){
        System.out.println(userService.findAllUsers().size());
    }

    @Test
    public void testFindUserByName(){
        User user = userService.findUserByName("test");
        assertNotNull(user);
        assertEquals("测试", user.getDescription());
    }

    @Test
    public void testFindById(){
        User user = userService.findUserById(2);
        assertEquals("用户", user.getDescription());
    }
}
