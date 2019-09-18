package cn.crabime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FilterInputStreamPractice {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\admin\\Desktop\\logs\\test_read.log"));
        byte[] bytes = new byte[1024];
        int length;
        long skippedBytes = fileInputStream.skip(4);
        System.out.println("跳过字节数：" + skippedBytes);
        while ((length = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
            System.out.println(new String(bytes, Charset.forName("GBK")));
        }
    }
}
