package com.gooalgene.mock.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by crabime on 11/5/17.
 * 这里为什么测试cookie返回的总是空!测试错误
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-mvc.xml"}))
public class ModelWithCookieControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockHttpServletRequestBuilder defaultRequestBuilder = get("/model/cookieModel");

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .defaultRequest(defaultRequestBuilder)
                .alwaysDo(result -> setSessionBackOnRequestBuilder(defaultRequestBuilder, result.getRequest()))
                .build();

    }

    @Test
    public void testCookieModelTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/model/cookieModel").param("id", "2"))
                .andExpect(status().isOk())
                .andReturn();
        Cookie cookie = mvcResult.getResponse().getCookie("name");
        System.out.println(cookie.getValue());
    }

    private MockHttpServletRequest setSessionBackOnRequestBuilder(final MockHttpServletRequestBuilder requestBuilder,
                                                                  final MockHttpServletRequest mockHttpServletRequest){
        requestBuilder.session((MockHttpSession) mockHttpServletRequest.getSession());
        return mockHttpServletRequest;
    }
}
