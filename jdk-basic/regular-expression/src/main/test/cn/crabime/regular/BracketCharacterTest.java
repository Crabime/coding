package cn.crabime.regular;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/23/17.
 * 使用括号,括号中数字为非复数n,表示匹配前面字符或字符串n次
 * 如:ab{1}
 */
public class BracketCharacterTest extends TestCase {

    /**
     * 包含1个字符
     */
    @Test
    public void testBracketIncludeOne(){
        final String regex = "zo{1}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("zo");
        assertTrue(matcher.matches());
    }

    /**
     * 前面不能包含匹配字符,如zo{0}只能匹配z,不能匹配zoo、zol、zo
     * 包含1个字符或者多个匹配字符,均错误
     */
    @Test
    public void testBracketIncludeZero(){
        final String regex = "zo{0}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("z"); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("zo"); //错误
        assertFalse(matcher.matches());
        matcher = compile.matcher("zoo"); //错误
        assertFalse(matcher.matches());
        matcher = compile.matcher("zol"); //错误
        assertFalse(matcher.matches());
    }

    /**
     * 使用group匹配方式,匹配括号中的字符串
     * 如:(zo){1}只能匹配zo,不能匹配""、zoo、zozo
     */
    @Test
    public void testBracketWholeMatches(){
        final String regex = "(zo){1}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("zo"); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("zozo"); //失败
        assertFalse(matcher.matches());
    }

    /**
     * 如果括号中匹配元素为0,则表示匹配空字符串,填入任何元素均错误
     */
    @Test
    public void testBracketMeaningless(){
        final String regex = "(zo){0}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(""); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("zo"); //失败
        assertFalse(matcher.matches());
    }

    /**
     * 如果括号中的数字大于1,则表示匹配连续出现的字符串次数
     * 如:(zo){2}匹配zozo,不能匹配zolzo等其它元素
     */
    @Test
    public void testBracketMatchesOneMore(){
        final String regex = "(zo){2}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("zozo"); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("zolzo");
        assertFalse(matcher.matches());
    }

    /**
     * 如果括号是{n,},则表示必须匹配n个或n个以上该元素
     * 如o{2,}能匹配oo, ""、o均错误
     */
    @Test
    public void testBracketMatchesMoreThanN(){
        final String regex = "o{2,}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("oo");
        assertTrue(matcher.matches());
        matcher = compile.matcher("oooooo");
        assertTrue(matcher.matches());
        matcher = compile.matcher("o");
        assertFalse(matcher.matches());
    }

    /**
     * 如果括号是{n,m},则表示必须匹配最少n个,最多m个匹配元素
     * 注意:","两边不能出现空格
     * 如o{2,5}能匹配oo、ooooo,不能匹配o、oooooo(6个o)、ol
     * o{0,1}等价于o?
     */
    @Test
    public void testBracketMatchesMinAndMax(){
        final String regex = "o{2,5}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("oo"); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("ooooo"); //正确
        assertTrue(matcher.matches());
        matcher = compile.matcher("o"); //错误
        assertFalse(matcher.matches());
        matcher = compile.matcher("oooooo"); //错误
        assertFalse(matcher.matches());
        matcher = compile.matcher("ol"); //错误
        assertFalse(matcher.matches());
    }
}
