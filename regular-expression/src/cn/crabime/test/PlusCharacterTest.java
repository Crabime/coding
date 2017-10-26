package cn.crabime.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 使用+特殊字符,表示该符号前面必须有一个以上前一位的字符
 * 如: ab+ 表示+前面必须有一个以上的b元素,如果是a则匹配不上
 * 等价于{1,}
 */
public class PlusCharacterTest extends TestCase {
    private final String regex = "zo+";
    private Pattern compile;
    private Matcher matcher;

    @Override
    protected void setUp() throws Exception {
        compile = Pattern.compile(regex);
    }

    /**
     * +前面只有一个o字符
     */
    @Test
    public void testPlusBeforeOne(){
        matcher = compile.matcher("zo");
        assertTrue(matcher.matches());
    }

    /**
     * +前面匹配多个o字符
     */
    @Test
    public void testPlusBeforeMultiple(){
        matcher = compile.matcher("zooo");
        assertTrue(matcher.matches());
    }

    /**
     * +前面没有o字符,出错!
     */
    @Test
    public void testPlusBeforeZero(){
        matcher = compile.matcher("z");
        assertFalse(matcher.matches());
    }

    /**
     * +前面有一个o字符,但是后面不是该字符,
     * 或者+前面直接不是o字符,都匹配失败
     */
    @Test
    public void testPlusReplaceWrong(){
        matcher = compile.matcher("zol");
        assertFalse(matcher.matches());
        matcher = compile.matcher("zl");
        assertFalse(matcher.matches());
    }
}
