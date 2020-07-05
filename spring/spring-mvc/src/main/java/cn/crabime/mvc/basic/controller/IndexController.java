package cn.crabime.mvc.basic.controller;

import cn.crabime.mvc.basic.PdfBean;
import cn.crabime.mvc.basic.service.SimpleCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

@Controller
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SimpleCacheService simpleCacheService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/abc")
    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }

    @RequestMapping(value = "/grb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ContentBean getRandomBean(HttpServletRequest request, HttpServletResponse response) {
        String bizCode = request.getHeader("bizCode");
        if (StringUtils.isEmpty(bizCode)) {
            throw new IllegalArgumentException("未携带用户身份");
        }

        StringBuffer requestURL = request.getRequestURL();
        logger.info("当前URL为：{}", requestURL.toString());
        String name = request.getParameter("name");

        if (simpleCacheService.get(name) != null) {
            name = simpleCacheService.getProperName(name);
        }
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            Optional<Cookie> nameCookie = Arrays.stream(cookies).filter(c -> "name".equals(c.getName())).findFirst();
            if (!nameCookie.isPresent()) {
                cookie = new Cookie("name", name);
            }
        } else {
            cookie = new Cookie("name", name);
        }
        response.addCookie(cookie);
        String value = request.getParameter("age");
        int age = 0;
        try {
            age = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("val只能为数字");
        }
        ContentBean ct = new ContentBean(name, age);
        simpleCacheService.put(name, ct);
        return ct;
    }

    @RequestMapping(value = "grk", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ContentBean getSearchedKey(@RequestParam(name = "name", required = false)String name, HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Optional<Cookie> nameCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("name")).findFirst();
                if (nameCookie.isPresent()) {
                    name = nameCookie.get().getValue();
                }
            }
        }
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        Object result = simpleCacheService.get(name);
        if (null != result) {
            return (ContentBean) result;
        }
        return null;
    }

    @RequestMapping(value = "gyrb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "text/yaml")
    @ResponseBody
    public ContentBean getYamlRandomBean(@RequestBody PdfBean pdfBean) {
        String name = pdfBean.getName();
        return new ContentBean("name:" + name, 25);
    }

    private static class ContentBean {
        String name;

        int age;

        public ContentBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
