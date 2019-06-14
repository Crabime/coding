package cn.crabime.regular;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 1/13/18.
 */
public class AdvanceSearchTest extends TestCase {

    @Test
    public void testMatchSpecialCharacter(){
        String regex = "(^[^a-zA-Z0-9]*)(\\d+)([^a-zA-Z0-9]*$)";
        String str = ".&5123;";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        System.out.println(matcher.group());
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
        System.out.println("\nnew regex pattern");
        str = ".$5123*";
        matcher = pattern.matcher(str);
        matcher.find();
        System.out.println(matcher.group(2));
        str = "5123*";
        matcher = pattern.matcher(str);
        matcher.find();
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
    }

    /**
     * 取出非字母和数字
     */
    @Test
    public void testRemoveNonNum(){
        String regex = "(^[^a-zA-Z0-9]*)([A-Za-z\\d\\s;,]+)([^a-zA-Z0-9]*$)";
        String str = "  .01G0049000;01G0049000 ,01G0049000 ";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group(2));
        }
    }

    @Test
    public void testMatchGene(){
        String deviation = "Glyma01G00100";
        String regex = "[([0-9]*(?<=G)[0-9]*)|((?=[0-9])*G(?<=[0-9]))]";
        String regex2 = "((?<=[a-zA-Z.])[0-9G]+|(?<![a-zA-Z.])[0-9G]+$)";
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "01G00100";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "Glyma.01G00100";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
