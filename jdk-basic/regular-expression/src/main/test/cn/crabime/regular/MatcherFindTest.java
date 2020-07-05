package cn.crabime.regular;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 9/24/17.
 */
public class MatcherFindTest extends TestCase {

    @Test
    public void testFind(){
        String regex = "\\w+";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("Evening is linnet's swing");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    public void testExcludeIllegalCharacter() {
        String regexp = "[0-9|,]+";
        Pattern pattern = Pattern.compile(regexp);
        Assert.assertTrue(pattern.matcher("1234").matches());
        Assert.assertFalse(pattern.matcher("1234a").matches());
        Assert.assertTrue(pattern.matcher("1234,").matches());
        Assert.assertTrue(pattern.matcher("1234,445").matches());
    }
}
