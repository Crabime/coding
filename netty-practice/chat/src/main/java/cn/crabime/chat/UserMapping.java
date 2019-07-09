package cn.crabime.chat;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class UserMapping {

    private String onum;

    private Channel channel;

    private List<String> onlineFriends;

    private List<String> offlineFriends;

    public String getOnum() {
        return onum;
    }

    public void setOnum(String onum) {
        this.onum = onum;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<String> getOnlineFriends() {
        return onlineFriends;
    }

    public void setOnlineFriends(List<String> onlineFriends) {
        this.onlineFriends = onlineFriends;
    }

    public List<String> getOfflineFriends() {
        return offlineFriends;
    }

    public void setOfflineFriends(List<String> offlineFriends) {
        this.offlineFriends = offlineFriends;
    }
}
