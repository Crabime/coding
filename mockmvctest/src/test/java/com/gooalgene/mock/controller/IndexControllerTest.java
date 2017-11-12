package com.gooalgene.mock.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by crabime on 11/2/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-mvc.xml"}))
public class IndexControllerTest extends TestCase {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindAllUsersController() throws Exception{
        mockMvc.perform(get("/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
        .andReturn();
    }

    @Test
    public void testFindUserByName() throws Exception{
        MvcResult result = mockMvc.perform(get("/findByName")
        .param("name", "test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("{\"id\":3,\"name\":\"test\",\"description\":\"测试\"}"))
                .andExpect(jsonPath("($.description=='测试')").isBoolean())
                .andReturn();
    }
}
