package cn.crabime.practice.mybatis.perf;

import java.util.Random;

public class RandomUtil {

    static Random random;

    final static Integer GAP = 64;

    final static Integer START = 58;

    static {
        random = new Random();
    }

    public static String generate(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = (char) (random.nextInt(START) + GAP);
            sb.append(c);
        }
        return sb.toString();
    }
}
