package beans;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by crabime on 10/14/17.
 */
public class YamlTest extends TestCase {

    @Test
    public void testJdkStringFormat(){
        Connection connection = new Connection();
        connection.setUrl("https://github.com/Crabime");
        connection.setPoolSize(10);
        System.out.println(connection.toString());
    }
}
