package cn.crabime.practice.mock;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 模拟耗时动作
 */
public class SpyCalcProcedure {

    Random RANDOM = new Random();

    public String calcProcess(int count) {
        final byte[] buffer = new byte[count];
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RANDOM.nextBytes(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
