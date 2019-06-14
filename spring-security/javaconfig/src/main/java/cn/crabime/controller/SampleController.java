package cn.crabime.controller;

import cn.crabime.beans.School;
import cn.crabime.beans.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
@RequestMapping(value = "/sample")
public class SampleController {

    @RequestMapping(value = "/sm", method = RequestMethod.GET)
    @ResponseBody
    public String getSample() {
        return "hello java config spring mvc";
    }

    /**
     * 直接JSON格式返回，通过jackson工具，返回的json字符串不受Formatter影响
     */
    @RequestMapping(value = "/su", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public User sampleUser() {
        return generateUser();
    }

    @ResponseBody
    @RequestMapping(value = "/sux", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public User sampleUserWithXml(HttpServletRequest request) {
        return generateUser();
    }

    @RequestMapping(value = "/suview")
    public String suview(Model model) {
        model.addAttribute("user", generateUser());
        return "formattertest";
    }

    private User generateUser() {
        Random random = new Random();
        int sid = random.nextInt(100) + 1000;
        int uid = random.nextInt(100) + 100;
        School school = new School();
        school.setId(sid);
        school.setName("沈阳理工大学");
        User user = new User();
        user.setId(uid);
        user.setUsername("张三");
        user.setSchool(school);
        return user;
    }
}
