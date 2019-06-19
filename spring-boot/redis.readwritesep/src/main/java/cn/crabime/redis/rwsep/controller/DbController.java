package cn.crabime.redis.rwsep.controller;

import cn.crabime.redis.rwsep.beans.PGene;
import cn.crabime.redis.rwsep.service.RedisService;
import cn.crabime.redis.rwsep.service.PGeneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/db")
public class DbController {

    private final static Logger logger = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private PGeneService pGeneService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 将数据库中数据全量写入Redis数据库中，这里只包含geneId、locus
     * hash key规则：hash:pig:gene
     */
    @RequestMapping(value = "/batchinsert", method = RequestMethod.GET)
    @ResponseBody
    public String insertValue(HttpServletRequest request) {
        String species = request.getParameter("species");
        if (species == null || species.equals("")) {
            return "传入species不能为空";
        }
        int pageSize = 100;
        StringBuilder hashKey = new StringBuilder("hash:pig:gene").append(species);
        for (int i = 0; i < 100; i++) {
            int pageStart = i * pageSize;
            List<PGene> pGeneList = pGeneService.findGeneBySpecies(species, pageStart, pageSize);
            Map<String, String> geneIdAndLocus = pGeneList.stream().collect(Collectors.toMap(PGene::getGeneId, PGene::getLocus));
            redisTemplate.opsForHash().putAll(hashKey.toString(), geneIdAndLocus);
            if (pGeneList.size() < pageSize) break;
        }
        return "插入成功";
    }

    @RequestMapping(value = "/batchdelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteHash(@RequestParam("hkey") String hashKey) {
        // TODO: 6/19/19 增加hscan使用和hdel功能 
        BoundHashOperations<Object, Object, Object> boundHashOperations = redisTemplate.boundHashOps(hashKey);
        Cursor<Map.Entry<Object, Object>> cursor = boundHashOperations.scan(ScanOptions.scanOptions().count(20).build());
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> next = cursor.next();
            logger.info("key={}, value={}", next.getKey(), next.getValue());
        }
        return "操作成功";
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getVal(@RequestParam(name = "key") String key) {
        String value = (String) redisService.get(key);
        logger.info("这里为了获取堆栈信息，key={}返回值为{}", key, value);
        return value;
    }

    @RequestMapping(value = "/getspec")
    @ResponseBody
    public List<PGene> getSpecGene(@RequestParam(name = "species") String species,
                                   @RequestParam("pageNo") int pageNo,
                                   @RequestParam("pageSize") int pageSize) {
        int startPage = (pageNo - 1) * pageSize;
        return pGeneService.findGeneBySpecies(species, startPage, pageSize);
    }

}
