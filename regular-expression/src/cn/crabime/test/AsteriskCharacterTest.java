package cn.crabime.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 使用*特殊字符(包含0个或多个),等价于{0,}
 */
public class AsteriskCharacterTest extends TestCase {
    private final String regex = "zo*";
    private Pattern compile;
    private Matcher matcher;

    @Override
    protected void setUp() throws Exception {
        compile = Pattern.compile(regex);
    }

    /**
     * 匹配前面0个
     */
    @Test
    public void testAsteriskBeforeZero(){
        matcher = compile.matcher("z");
        assertTrue(matcher.matches());
    }

    /**
     * 匹配前面包含一个(原字符串"o")
     */
    @Test
    public void testAsteriskBeforeOne(){
        matcher = compile.matcher("zo");
        assertTrue(matcher.matches());
    }

    /**
     * *符号匹配任意多个字符串"o"
     */
    @Test
    public void testAsteriskReplaceMultiple(){
        matcher = compile.matcher("zooo");
        assertTrue(matcher.matches());
    }

    /**
     * 匹配*前面的字符并非当前*前面字符
     * 或者前面一个是o但是*符号位置或者后面不是o字符,错误!
     */
    @Test
    public void testAsteriskReplaceWrong(){
        matcher = compile.matcher("zl");
        assertFalse(matcher.matches());
        matcher = compile.matcher("zol");
        assertFalse(matcher.matches());
    }
}
