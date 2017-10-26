package cn.crabime.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * 邮箱匹配方式
 */
public class MatcherMail {

    public static void main(String[] args) {
        String mail = "crabime@gmail.com";
        String regex = "^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-Z0-9]-*){1,}\\.){1,3}[a-zA-Z\\-]{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
}
