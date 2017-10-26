package cn.crabime.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * Created by crabime on 5/11/17.
 * 匹配用户名:字母开头+数字/字母/下划线
 */
public class MatcherUserName {
    public static void main(String[] args) {
        String username = "Crabime0123";
        //+表示前面匹配的可以出现一次或多次
        String regex = "^[a-zA-Z][a-zA-Z1-9_]+$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(username);
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
}
