package cn.crabime;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 练习spring BeanUtils Api
 */
public class BeanUtilsTest extends TestCase {

    @Test
    public void testInstantiateUsingDefaultConstructor() {
        Date date = BeanUtils.instantiate(Date.class);
        assertNotNull(date);
    }
}
