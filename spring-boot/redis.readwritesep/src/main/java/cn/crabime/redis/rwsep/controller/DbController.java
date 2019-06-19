package cn.crabime.redis.rwsep.controller;

import cn.crabime.redis.rwsep.beans.PGene;
import cn.crabime.redis.rwsep.service.MyDbService;
import cn.crabime.redis.rwsep.service.PGeneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/db")
public class DbController {

    private final static Logger logger = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private MyDbService myDbService;

    @Autowired
    private PGeneService pGeneService;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public String insertValue(@RequestParam(name = "key") String key, @RequestParam(name = "val") String value) {
        logger.info("插入redis中的key={},value={}", key, value);
        myDbService.insert(key, value);
        return "插入成功";
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getVal(@RequestParam(name = "key") String key) {
        String value = (String) myDbService.get(key);
        logger.info("这里为了获取堆栈信息，key={}返回值为{}", key, value);
        return value;
    }

    @RequestMapping(value = "/getspec")
    @ResponseBody
    public List<PGene> getSpecGene(@RequestParam(name = "species") String species) {
        return pGeneService.findGeneBySpecies(species, 0, 0);
    }

}
