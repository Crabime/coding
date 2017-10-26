package cn.crabime.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * Created by crabime on 5/13/17.
 * 匹配用户输入电话号码
 */
public class MatcherInputPhone {
    public static void main(String[] args) throws Exception{
        BufferedReader br;
        //只要放在工程文件下面,都可以被FileReader解析
        br = new BufferedReader(new FileReader("test/phones"));
        String regex = "^1[3|4|5|8][0-9]\\d{8}$";
//        String phone = "13236632258";
        Pattern compile = Pattern.compile(regex);
//        Matcher matcher = compile.matcher(phone);
        String content;
        while ((content = br.readLine()) != null){
            System.out.println(content);
            Matcher matcher = compile.matcher(content);
            if (matcher.matches()){
                System.out.println("里面号码都正常");
            } else {
                System.out.println("号码有异常");
            }
        }
//        if (matcher.matches()){
//            System.out.println("匹配成功");
//        } else {
//            System.out.println("匹配失败");
//        }
    }
}
