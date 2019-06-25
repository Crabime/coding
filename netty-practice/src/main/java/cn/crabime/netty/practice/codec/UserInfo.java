package cn.crabime.netty.practice.codec;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;

    private int userID;

    public UserInfo buildUserName(String name) {
        this.userName = name;
        return this;
    }

    public UserInfo buildUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public byte[] codec(ByteBuffer buffer) {
        buffer.clear();

        byte[] userNameBytes = this.userName.getBytes();

        buffer.putInt(userNameBytes.length);
        buffer.put(userNameBytes);
        buffer.putInt(this.userID);
        // 读写转换
        buffer.flip();

        userNameBytes = null;
        // 将字节缓冲区中已有字节读入到字节数组中
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);

        return result;
    }
}
