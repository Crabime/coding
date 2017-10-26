package cn.crabime.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * Created by crabime on 5/11/17.
 * 匹配手机号码
 */
public class MatcherTelephone {
    public static void main(String[] args) {
        String phone = "15527811219";
        String regex = "^1[3|4|5|8][0-9]\\d{8}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(phone);
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
}
