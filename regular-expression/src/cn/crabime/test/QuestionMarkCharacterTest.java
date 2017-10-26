package cn.crabime.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 使用?特殊字符,表示该符号前面只能匹配0或1个字符
 * 如:abc?则匹配ab、abc,abcd、abcc则表错
 */
public class QuestionMarkCharacterTest extends TestCase {
    private final String regex = "zo?";
    private Pattern compile;
    private Matcher matcher;

    @Override
    protected void setUp() throws Exception {
        compile = Pattern.compile(regex);
    }

    /**
     * ?前面匹配一个
     */
    @Test
    public void testQuestionMarkBeforeOne(){
        matcher = compile.matcher("zo");
        assertTrue(matcher.matches());
    }

    /**
     * ?前面匹配0个
     */
    @Test
    public void testQuestionMarkBeforeZero(){
        matcher = compile.matcher("z");
        assertTrue(matcher.matches());
    }

    /**
     * ?前面匹配两个以上,或者?前面字符不是o
     */
    @Test
    public void testQuestionMarkBeforeMultipleOrWrong(){
        matcher = compile.matcher("zoo");
        assertFalse(matcher.matches());
        matcher = compile.matcher("zl");
        assertFalse(matcher.matches());
    }
}
