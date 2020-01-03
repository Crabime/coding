package cn.crabime.mvc.basic.controller;

import cn.crabime.mvc.basic.PdfBean;
import cn.crabime.mvc.basic.service.SimpleCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/grb", method = RequestMethod.GET)
    @ResponseBody
    public ContentBean getRandomBean(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        logger.info("当前URL为：{}", requestURL.toString());
        String key = request.getParameter("key");
        ContentBean ct = new ContentBean("张三", 25);
        simpleCacheService.put(key, ct);
        return ct;
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
