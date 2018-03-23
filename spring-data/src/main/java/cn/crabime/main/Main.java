package cn.crabime.main;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by crabime on 3/21/18.
 */
@Component
public class Main {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void start() {
        Criteria criteria = Criteria.where("alt").is("C");
        Query query = new Query(criteria);
        final double[] pos = new double[1];
        mongoTemplate.executeQuery(query, "SNP", new DocumentCallbackHandler() {
            public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
                double result = Double.parseDouble(dbObject.get("pos").toString());
                pos[0] = result;
            }
        });
        System.out.println("current gene pos" + pos[0]);
    }

    public static void main(String[] args) {
        ApplicationContext atx = new ClassPathXmlApplicationContext("classpath:spring-mongodb.xml");
        final Main main = atx.getBean("main", Main.class);
        main.start();
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5000);
                        main.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
