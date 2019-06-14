package cn.crabime.regular;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 5/13/17.
 */
public class ExpressionUnitTest {

    @Test
    public void testMatchNormal() throws Exception{
        String regex = "\\(\\d{3}\\)\\d+$|\\d{3}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("(123)4");
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }

    /**
     * 测试匹配电话号码
     */
    @Test
    public void testMatchPhone() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("test/phones"));
        String regex = "^1[3|4|5|8]\\d{9}$";
        Pattern compile = Pattern.compile(regex);
        String content = null;
        while ((content = br.readLine()) != null){
            System.out.print(content + " ==> ");
            Matcher matcher = compile.matcher(content);
            if (matcher.matches()){
                System.out.println("匹配成功");
            }else {
                System.out.println("匹配失败");
            }
        }
    }
}
