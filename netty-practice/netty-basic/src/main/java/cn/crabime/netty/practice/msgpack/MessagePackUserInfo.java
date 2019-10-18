package cn.crabime.netty.practice.msgpack;

import org.msgpack.annotation.Message;

@Message
public class MessagePackUserInfo {

    private String userName;

    private int userID;

    public MessagePackUserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
