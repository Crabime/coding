package cn.crabime.redis.rwsep.controller;

import cn.crabime.redis.rwsep.service.MyDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/db")
public class DbController {

    private final static Logger logger = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private MyDbService myDbService;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public void insertValue() {
        String key = "测试key";
        String value = "测试value";
        logger.info("进入到insertValue方法中");
        myDbService.insert(key, value);
    }
}
