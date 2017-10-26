package annotation;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by crabime on 9/17/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(locations = "classpath:beans.xml"))
public class LookupAnnotationTest extends TestCase {

    @Autowired
    private Factory factory;

    @Test
    public void testLookupAnnotation(){
        factory.produceLining();
    }
}
