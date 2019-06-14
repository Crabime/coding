package cn.crabime.regular;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/24/17.
 * 使用非捕获组,以(?)开头的组是存的非捕获组,它不捕获文本,也不针对组合进行计数
 * 不捕获文本表示它只符合合格匹配,如果满足条件,那么通过group返回的是捕获组的内容,
 * 如(?<!4)56(?=9)可以匹配5569,但是通过捕获组拿到的是56,非捕获组均不能输出
 * 当然这里是无法匹配4569(先行断言错误)、5568(后发断言错误)
 */
public class NonCaptureGroupTest extends TestCase {

    /**
     * 使用零宽度负lookbehind匹配表达式
     * (?<!X):仅当X不在此位置的左侧时才匹配(零宽度负后发断言)
     * (?=X):仅当X在此为止的右侧时才继续匹配(零宽度正先行断言)
     *
     */
    @Test
    public void testLookBehind(){
        String regex = "(?<!4)56(?=9)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("5569");
        while (matcher.find()){
            System.out.println(matcher.group(0));
        }
        System.out.println("=========>");
        matcher = compile.matcher("4569"); //先行断言必须为4,如果不是4则返回错误
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        System.out.println("=========>");
        matcher = compile.matcher("6568"); //后发断言错误,这里也是无法匹配
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    /**
     * 使用非捕获组获取到匹配的文本一定不包含非捕获性文本
     * 如下面第一个捕获组输出的为当前捕获组的捕获的所有文本,它就不包含(?<!C)
     */
    @Test
    public void testNoneCaptureGroup(){
        String regex = "(?<!c)a(\\d+)bd";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("da12bka3434bdca4343bdca234bm");
        while (matcher.find()){
            System.out.println(matcher.group(1)); //第二个捕获组获取到的为匹配上的(\\d+)内容
            System.out.println(matcher.group(0));
        }
    }

    /**
     * 使用零宽度正先行断言(?=X):当子表达式X在此位置的右侧时才能继续匹配
     * 抛去正先行断言部分
     */
    @Test
    public void testPositiveLookAhead(){
        String regex = "(\\d+){4}(?=[A-Z])*";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("3211A");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        matcher = compile.matcher("3211AZ");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        matcher = compile.matcher("3211a");
        assertFalse("匹配3211a失败", matcher.matches());
    }

    /**
     * 使用零宽度负先行断言(?!X),仅当X不在此表达式右侧时才进行匹配
     */
    @Test
    public void testNegativeLookAhead(){
        String regex = "(\\d+){4}(?![A-Z])";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("3211a");
        while (matcher.find()){
            System.out.println(matcher.group()); //这里也不会输出非捕获组内容
        }
        matcher = compile.matcher("3211A");
        assertFalse("3211A匹配失败", matcher.matches());
    }

    /**
     * 使用零宽度正后发断言(?<=X):当子表达式X在此位置左侧时才匹配
     */
    @Test
    public void testPositiveLookBehind(){
        String regex = "(?<=[C-E])(\\d+){4}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("C3214");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        matcher = compile.matcher("c3214"); //在左侧表达式不合法,无法匹配
        assertFalse(matcher.matches());
    }

    /**
     * 使用零宽度负后发断言(?<!X):当子表达式X不在此位置左侧时才匹配
     */
    @Test
    public void testNegativeLookBehind(){
        String regex = "(?<![C-E])(\\d+){4}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("A3214");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        matcher = compile.matcher("C3214");
        assertFalse(matcher.matches());
    }
}
