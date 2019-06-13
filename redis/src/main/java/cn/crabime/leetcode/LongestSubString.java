package cn.crabime.leetcode;

public class LongestSubString {

    public int lengthOfLongestSubstring(String s) {
        char[] str = s.toCharArray();
        int[] b = new int[256];
        int res = 0, left = 0;
        for (int i = 0; i < str.length; i++) {
            left = Math.max(left, b[str[i]]);
            // 字符串长度
            res = Math.max(res, i - left + 1);
            // 纪录该字符在map中的位置，map的key为对应ASCII值，value为字符串中的位置
            b[str[i]] = i + 1;
        }
        return res;
    }

    private void testSimpleString(String str) {
        System.out.println("====== 输入字符串" + str + " ======");
        System.out.println(lengthOfLongestSubstring(str));
        System.out.println();
    }

    public static void main(String[] args) {
        LongestSubString subString = new LongestSubString();
        subString.testSimpleString("abcabcabcd");
        subString.testSimpleString("bbbbb");
    }
}
