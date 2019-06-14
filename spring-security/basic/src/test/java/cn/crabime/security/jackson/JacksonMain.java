package cn.crabime.security.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by crabime on 8/5/18.
 */
public class JacksonMain {

    public static void main(String[] args) {
        String init = "{\n" +
                "    \"@class\": \"cn.crabime.security.jackson.Cat\",\n" +
                "    \"type\": \"cat\",\n" +
                "    \"name\": \"Android\",\n" +
                "    \"sleepingTime\": 8.5\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            Cat cat = (Cat) mapper.readValue(init, AbstractAnimal.class);
            System.out.println("猫睡眠时间为:" + cat.getSleepingTime()
                + ",名字为:" + cat.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
