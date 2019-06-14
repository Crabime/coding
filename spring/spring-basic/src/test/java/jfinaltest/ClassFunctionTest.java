package jfinaltest;

import jfinal.ClassFunction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.Set;

/**
 * Created by crabime on 6/29/17.
 */

public class ClassFunctionTest {

    public static Log log = LogFactory.getLog(ClassFunctionTest.class);
    /**
     * 测试ClassFunction能否扫描一个包下所有文件
     */
    @Test
    public void testInterceptPack(){
        Set<Class<?>> classes = ClassFunction.getClasses("cn.crabime");
        log.info("所有类总数为:" + classes.size());
    }
}
