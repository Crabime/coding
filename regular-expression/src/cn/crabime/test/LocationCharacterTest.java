package cn.crabime.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 定位符使用方式
 */
public class LocationCharacterTest extends TestCase {

    /**
     * 匹配字符串开始符号^
     */
    @Test
    public void testBeforeCharacter(){
        final String regex = "^a[a-z]{2}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("acd");
        assertTrue(matcher.matches());
    }

    /**
     * 匹配任意多个数字、字母、下划线,但是最后一位需要是$符号
     */
    @Test
    public void testLastCharacter(){
        final String regex = "\\w+\\$$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("acd$");
        assertTrue(matcher.matches());
    }
}
