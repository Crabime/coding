package cn.crabime.redis.rwsep.controller;

import cn.crabime.redis.rwsep.beans.PGene;
import cn.crabime.redis.rwsep.service.PGeneService;
import cn.crabime.redis.rwsep.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

// TODO: 2019/6/24 增加redis HyperLogLog功能统计登录的session个数
@Controller
@RequestMapping(value = "/db")
public class DbController {

    private final static Logger logger = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private PGeneService pGeneService;

    // TODO: 2019/6/21 这里复现启动时连接超时问题
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 将数据库中数据全量写入Redis数据库中，这里只包含geneId、locus
     * hash key规则：hash:pig:gene
     */
    @RequestMapping(value = "/bipg", method = RequestMethod.POST)
    @ResponseBody
    public String batchInsertPigGene(HttpServletRequest request) {
        String species = request.getParameter("species");
        if (species == null || species.equals("")) {
            return "传入species不能为空";
        }

        int pageSize = 100;
        StringBuilder hashKey = new StringBuilder("hash:pig:gene:1:").append(species);
        // 判断hash中key是否达到10000
        if (redisTemplate.hasKey(hashKey.toString())){
            if (redisTemplate.boundHashOps(hashKey.toString()).size() == 10000) {
                logger.warn("{} key已达到10000万个，暂停插入", hashKey.toString());
                return "Full Now";
            }
        }
        for (int i = 0; i < 100; i++) {
            int pageStart = i * pageSize;
            List<PGene> pGeneList = pGeneService.findGeneBySpecies(species, pageStart, pageSize);
            Map<String, String> geneIdAndLocus = pGeneList.stream().collect(Collectors.toMap(PGene::getGeneId, PGene::getLocus));
            redisTemplate.opsForHash().putAll(hashKey.toString(), geneIdAndLocus);
            if (pGeneList.size() < pageSize) break;
        }
        return "插入成功";
    }

    @RequestMapping(value = "/bipgup", method = RequestMethod.POST)
    @ResponseBody
    public String batchInsertPigGeneUsingPipeline(HttpServletRequest request) {
        String species = request.getParameter("species");
        if (species == null || species.equals("")) {
            return "传入species不能为空";
        }

        int pageSize = 100;
        StringBuilder hashKey = new StringBuilder("hash:pig:gene:2:").append(species);
        // 判断hash中key是否达到10000
        if (redisTemplate.hasKey(hashKey.toString())){
            if (redisTemplate.boundHashOps(hashKey.toString()).size() == 10000) {
                logger.warn("{} key已达到10000万个，暂停插入", hashKey.toString());
                return "Full Now";
            }
        }
        for (int i = 100; i < 200; i++) {
            int pageStart = i * pageSize;
            List<PGene> pGeneList = pGeneService.findGeneBySpecies(species, pageStart, pageSize);
            Map<String, String> geneIdAndLocus = pGeneList.stream().collect(Collectors.toMap(PGene::getGeneId, PGene::getLocus));
            redisTemplate.executePipelined(new RedisCallback<Void>() {
                @Override
                public Void doInRedis(RedisConnection connection) throws DataAccessException {
                    geneIdAndLocus.forEach((k, v) -> connection.hSet(hashKey.toString().getBytes(), k.getBytes(), v.getBytes()));
                    return null;
                }
            });
            if (pGeneList.size() < pageSize) break;
        }
        return "插入成功";
    }

    /**
     * 获取Redis中key
     * @param key 匹配的key，如果传入的，如果传入的key为空，这里使用scan获取前10个，否则通过scan匹配获取前10个
     * @return redis中匹配为传入key的结果
     */
    @RequestMapping(value = "/gak", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAllKey(@RequestParam("key") String key) {
        List<String> result = new ArrayList<>();
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        Cursor<byte[]> cursor = null;

        if (null == key || key.equals("")) {
            logger.warn("gak方法中传入的key为空");
            ScanOptions scanOptions = ScanOptions.scanOptions().build();
            cursor = connection.scan(scanOptions);
        } else {
            ScanOptions scanOptions = ScanOptions.scanOptions().match(key).build();
            long startTime = System.currentTimeMillis();
            cursor = connection.scan(scanOptions);
            logger.info("获取{}对应的事件为:{}", key, (System.currentTimeMillis() - startTime));
        }

        while (cursor.hasNext()) {
            byte[] next = cursor.next();
            result.add(new String(next, Charset.forName("UTF-8")));
            logger.info("key={}", new String(next, Charset.forName("UTF-8")));
        }
        return result;
    }

    /**
     * 根据传入的geneId和species，找到值在Redis hash中的值
     * @return 如果传入的species为空，找到的值也为空，否则为该基因对应的位置locus
     */
    @RequestMapping(value = "/glbg", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLocusByGene(@RequestParam("gid") String geneId, @RequestParam("species") String species) {
        List<String> result = new ArrayList<>();
        String hashKey;
        if (null == geneId || geneId.equals("")) {
            logger.warn("传入的geneId不存在");
            return result;
        }
        if (null == species || species.equals("")) {
            logger.warn("传入的locus不存在");
            return result;
        }
        hashKey = "hash:pig:gene:" + species;
        ScanOptions keyScanOptions = ScanOptions.scanOptions().match(hashKey).build();
        // TODO: 2019/6/21 如何查看这个时候线程池中有多少个空闲连接
        Cursor<byte[]> scanRes = redisTemplate.getConnectionFactory().getConnection().scan(keyScanOptions);
        if (scanRes.hasNext()) {
            // TODO: 2019/6/21 启动时直接从一个大key中查找，复杂度不能为O(1)，保证查询有足够的时间从而查询超时
            String matchLocus = (String) redisTemplate.boundHashOps(hashKey).get(geneId);
            result.add(matchLocus);
        }
        return result;
    }


    /**
     * 启动时做一次hmget操作，由于hmget是一个O(N)的操作，时间足够长
     * @return gene、locus对应map
     */
    @RequestMapping(value = "/gmpg", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getMultipleLocusByGene(@RequestParam("gl") String geneList,
                                                      @RequestParam("species") String species) {
        Map<String, String> result = new HashMap<>();
        List<Object> inputGeneList = Arrays.asList(geneList.split(","));
        String hashKey = "hash:pig:gene:" + species;
        long startTime = System.currentTimeMillis();
        List<Object> locusList = redisTemplate.boundHashOps(hashKey).multiGet(inputGeneList);
        logger.info("hmget总耗时{}", (System.currentTimeMillis() - startTime));
        for (int i = 0; i < inputGeneList.size(); i++) {
            result.put((String) inputGeneList.get(i), (String) locusList.get(i));
        }
        return result;
    }

    /**
     * @param httpSession 在注解中增加HttpSession，Spring MVC会自动给我们创建session
     */
    @RequestMapping(value = "/gk", method = RequestMethod.GET)
    @ResponseBody
    public Object getVal(HttpServletRequest request, HttpSession httpSession) {
        String key = request.getParameter("key");
        String sessionId = httpSession.getId();
        logger.info("当前session id为：{}", sessionId);
        String value = null;
        if (redisTemplate.type("key").equals(DataType.STRING)) {
            value = (String) redisService.get(key);
            logger.info("这里为了获取堆栈信息，key={}返回值为{}", key, value);
        }
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
