package beans;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by crabime on 10/14/17.
 */
public class YamlCoreTest extends TestCase {

    private Yaml yaml;
    private InputStream is;

    @Before
    public void setUp(){
        yaml = new Yaml();
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.yml");
    }

    @Test
    public void testLoadYmlFile(){
        try {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer, 0, buffer.length)) != -1){
                String str = new String(buffer, 0, length);
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInterceptYmlFile(){
        Yaml yaml = new Yaml();
        Configuration configuration = yaml.loadAs(is, Configuration.class);
        System.out.println(configuration.toString());
    }
}
