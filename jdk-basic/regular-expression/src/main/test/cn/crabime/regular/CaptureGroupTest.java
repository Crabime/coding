package cn.crabime.regular;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/24/17.
 * 使用捕获组进行匹配长字符串
 */
public class CaptureGroupTest extends TestCase {

    @Test
    public void testBackReference(){
        String regex = "(['\"]).*";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("'abc");
        while (matcher.find()){
            System.out.println(matcher.group(0));
        }
    }

    /**
     * 使用group查询匹配身份证
     */
    @Test
    public void testMatchIdCard(){
        String regex = "(\\d+{6})((19\\d+{2})|((20(0[0-9])|(1[1-7])))((0[0-9])|(1[0-2])))((0[1-9])|([1,2][0-9])|(3[0,1]))(\\d+{3}[0-9A-Z])";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("421182199405202138");
        while (matcher.find()){
            System.out.println(matcher.group());
            System.out.println("前六位" + matcher.group(1));
            System.out.println("年龄:" + matcher.group(2));
            System.out.println("后六位:" + matcher.group(3));
        }

    }
}
