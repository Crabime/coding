package cn.crabime;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by crabime on 11/5/17.
 */
public class TweakConfigurationTest extends TestCase {
    private String example;

    @Before
    protected void setUp() throws Exception {
        example = "[\n" +
                "   {\n" +
                "      \"name\" : \"john\",\n" +
                "      \"gender\" : \"male\"\n" +
                "   },\n" +
                "   {\n" +
                "      \"name\" : \"ben\"\n" +
                "   }\n" +
                "]";
    }

    @Test
    public void testAlwaysReturnList(){
        Configuration configuration = Configuration.defaultConfiguration();
        String gender0 = JsonPath.using(configuration).parse(example).read("$[0]['gender']");
        assertEquals("male", gender0);
        try{
            String gender1 = JsonPath.using(configuration).parse(example).read("$[1]['gender']");
            fail();
        }catch (PathNotFoundException e){

        }
    }

    /**
     * 如果jsonpath中没有该值,直接返回null
     */
    @Test
    public void testReturnNullIfNone(){
        Configuration configuration = Configuration.defaultConfiguration();
        Configuration newConfig = configuration.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        String gender0 = JsonPath.using(newConfig).parse(example).read("$[0]['gender']");
        String gender1 = JsonPath.using(newConfig).parse(example).read("$[1]['gender']");
        assertEquals("male", gender0); //返回正常值
        assertNull(gender1); //返回空值
    }
}
