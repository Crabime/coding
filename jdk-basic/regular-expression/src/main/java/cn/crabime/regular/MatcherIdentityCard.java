package cn.crabime.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * Created by crabime on 5/11/17.
 * 匹配身份证号码
 */
public class MatcherIdentityCard {
    public static void main(String[] args) {
        String cardNum = "421182199405202138";
        String cardNum1 = "42118219940520213x";
        String regex = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|x|X)?$";
        Pattern compile = Pattern.compile(regex);
//        Matcher matcher = compile.matcher(cardNum);
        Matcher matcher = compile.matcher(cardNum1);
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
}
