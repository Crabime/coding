package cn.crabime.chat;

import java.io.Serializable;

/**
 * 聊天信息实体
 */
public class ChatMessage implements Serializable {

    /**
     * 消息类型
     */
    private String type;

    private String message;

    private String toNum;

    public ChatMessage() {
    }

    public ChatMessage(String type, String message, String toNum) {
        this.type = type;
        this.message = message;
        this.toNum = toNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToNum() {
        return toNum;
    }

    public void setToNum(String toNum) {
        this.toNum = toNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
