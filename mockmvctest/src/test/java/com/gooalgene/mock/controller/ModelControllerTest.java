package com.gooalgene.mock.controller;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by crabime on 11/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-mvc.xml"}))
public class ModelControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSimpleModelConvertValueTest() throws Exception {
        mockMvc.perform(
                get("/model/simpleModel"))
                .andExpect(status().isOk())
                .andExpect(view().name("simple-model"))
                .andExpect(forwardedUrl("/WEB-INF/views/simple-model.jsp"))
                .andReturn();
    }

    /**
     * 这种方式去测试cookie并行不通,常见{@link ModelWithCookieControllerTest}
     * @throws Exception
     */
    @Test
    public void testCookieModelTest() throws Exception {
        mockMvc.perform(get("/model/cookieModel").param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("simple-model"))
                .andExpect(model().attribute("name", "user"))
                .andReturn();
    }

    @Test
    public void testModelAndView() throws Exception {
        mockMvc.perform(get("/model/modelViewCombing"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "crabime"))
                .andReturn();
    }
}
