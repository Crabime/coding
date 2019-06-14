package cn.crabime.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabime
 * Created by crabime on 5/11/17.
 * 匹配网页URL
 */
public class MatcherUrl {
    public static void main(String[] args) {
        String url = "https://www.google.com";
        String url2 = "https://www.baidu.com/s?" +
                "wd=java%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%E5%AD%90%E8%A1%A8%E8%B" +
                "E%BE%E5%BC%8F&rsv_spt=1&rsv_iqid=0xd3b284cb0001ac60&issp=1&f=8&" +
                "rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=1&" +
                "oq=java%25E6%25AD%25A3%25E5%2588%2599%25E8%25A1%25A8%25E8%25BE%25BE%" +
                "25E5%25BC%258F&rsv_t=0b3849Z%2FxSRNSg9b4XgL5RFhnmxeA%2FKBVmN1hZMP8Yu22%" +
                "2BQF%2F29GLWaPBixKWH1h7Qfz&inputT=5260&rsv_pq=a3b5231f00022042&rsv_sug3=" +
                "93&rsv_sug1=54&rsv_sug7=100&rsv_sug2=0&rsv_sug4=7287";
        String regex = "^(https?|http|ftp|file)://[-A-Za-z0-9+$&%@#/?=~_|:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(url2);
        if (matcher.matches()){
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
}
