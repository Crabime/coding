package cn.crabime.netty.practice.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 测试jdk序列化与使用ByteBuffer进行二进制编解码技术对比
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(1001).buildUserName("Welcome to netty");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream ops = new ObjectOutputStream(bos);
        ops.writeObject(info);
        ops.flush();
        ops.close();

        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        bos.close();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] c = info.codec(buffer);
        System.out.println("ByteBuffer serializable length is : " + c.length);
    }
}
