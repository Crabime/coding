package cn.crabime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/mylogin", method = RequestMethod.GET)
    public String loginPage() {
        logger.info("来到login页面");
        return "mylogin";
    }
}
