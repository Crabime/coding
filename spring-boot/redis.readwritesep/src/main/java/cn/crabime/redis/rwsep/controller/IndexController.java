package cn.crabime.redis.rwsep.controller;

import cn.crabime.redis.rwsep.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("/")
    public String index(HttpSession httpSession) {
        String sessionId = httpSession.getId();
        logger.info("当前session id为：{}", sessionId);
        return "index";
    }

    @RequestMapping(value = "/us", method = RequestMethod.GET)
    public ModelAndView usPage() {
        ModelAndView view = new ModelAndView("uvpage");
        Long sessionCount = redisService.countUserSession();
        view.addObject("sessionCount", sessionCount);
        return view;
    }

}
