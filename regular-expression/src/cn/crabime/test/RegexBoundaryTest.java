package cn.crabime.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 使用\b与\B之间的区别
 * 参考文档:
 * <a href="http://blog.csdn.net/Marvel__Dead/article/details/53363955">
 *     \b与\B之间的区别</a>
 */
public class RegexBoundaryTest extends TestCase{

    /**
     * \b的用法:
     * 这里的\b，单词边界符能够匹配中文符号、英文符号、空格、制表符、回车符号，
     * 以及各种边界，比如单词在开头，单词在结尾。
     */
    @Test
    public void testSimpleBoundary(){
        String str = ",,,,2,";
        String regex = "\\b2\\b";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    /**
     * \B的用法
     * \B是非单词分界符，即可以查出是否包含某个字，如“代码大神”中是否包含“神”这个字。
     */
    @Test
    public void testUppercaseBoundary(){
        String str = "代码大神";
        String regex = "\\b神\\b";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    /**
     * 因为\b和\B是边界匹配符，所以一般不用来判断当前字符串是否符合某种规则，这样是不行的。
     * 所以\b和\B这种边界符一般用来获取，而不是用来判断、替换。
     */
    @Test
    public void testBoundaryMatches(){
        String str = " 1 ";
        String regex = "\\b1\\b";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        assertFalse(matcher.matches());
    }

    /**
     * \b不拒绝空格但接受符号
     *
     * 那么是不是有所启发了？？单词边界就是，单词和符号的边界，
     * 这里的单词可以是数字、英文单词、中文单词并且这些单词不互斥。
     * 而符号可以是英文符号、中文符号、空格、制表符、换行，而符号间不互斥。
     */
    @Test
    public void testLowerCaseMatches(){
        String sta="(？?213lang狼13我是华丽分界线中文问号和英文问号？???？我是华丽分界线空格 我是华丽分界线换行\n我是华丽分界线制表符   ";
        String regex="\\b";

        String arrays[]=sta.split(regex);
        for(String i:arrays){
            System.out.println("["+i+"]");
        }
    }

    /**
     * 就是因为\b是匹配这种边界。所以当我们想判断“ 1 ”
     * 用正则规则“\\b1\\b”是否匹配的时候，这是不能够匹配的。
     * 因为空格不是边界，而空格和1之间那个边界才是\b匹配的边界。
     */
    @Test
    public void testLowerCaseMatchesBoolean(){
        String str = "1";
        String regex = "\\b1\\b";
        assertTrue(str.matches(regex));
    }

    /**
     * B相当于[^\b]
     * \b的作用是单词和符号之间的边界。
     * 而我们分割的也是单词和符号之间的边界。
     * 所以它的相反面就是，单词和符号之间的边界不是我的边界，
     * 而单词和单词之间的边界和符号和符号之间的边界就是我的边界
     */
    @Test
    public void testUpperCase(){
        String sta="123lang北斗狼神-,,,,,???？？？-";
        String regex="\\B";

        String arrays[]=sta.split(regex);
        for(String i:arrays){
            System.out.println("["+i+"]");
        }
    }
}
