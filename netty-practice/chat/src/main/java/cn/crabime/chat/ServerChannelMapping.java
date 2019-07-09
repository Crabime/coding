package cn.crabime.chat;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 由于ServerChannelHandler每建立一个Channel连接时，均会创建一个该对象，
 * 所以这里使用单例模式，保证所有线程只能创建一个ServerChannelMapping实例。
 * map中保存客户端唯一的onum与Channel映射
 */
public class ServerChannelMapping {

    private ConcurrentHashMap<String, UserMapping> map = new ConcurrentHashMap<>(20);

    private static ServerChannelMapping mapping;

    private ServerChannelMapping() {

    }

    public static ServerChannelMapping getInstance() {
        if (mapping == null) {
            mapping = new ServerChannelMapping();
        }
        return mapping;
    }

    public void putContextIntoMap(String oNum, UserMapping mapping){
        this.map.put(oNum, mapping);
    }

    public Channel getChannelByNum(String oNum) {
        UserMapping mapping = this.map.get(oNum);
        if (mapping != null) {
            return mapping.getChannel();
        }
        return null;
    }

    public UserMapping getUserMappingByChannel(Channel channel) {
        return map.search(1, (k, v) -> {
            if (channel.id().asLongText().equals(v.getChannel().id().asLongText())) {
                return v;
            } else {
                return null;
            }
        });
    }

    public String getONumByChannel(Channel c) {
        return map.search(1, (k, v) -> {
            if (c.id().asLongText().equals(v.getChannel().id().asLongText())) {
                return k;
            } else {
                return null;
            }
        });
    }

    public CopyOnWriteArraySet<UserMapping> toggleOnlineNotice(String oNum) {
        CopyOnWriteArraySet<UserMapping> waitingList = new CopyOnWriteArraySet<>();
        this.map.search(1, (k, v) -> {
            List<String> offlineFriends = v.getOfflineFriends();
            if (offlineFriends !=null && offlineFriends.contains(oNum)) {
                waitingList.add(v);
            }
            return k;
        });
        return waitingList;
    }
}
