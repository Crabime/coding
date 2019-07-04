package cn.crabime.chat;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 由于ServerChannelHandler每建立一个Channel连接时，均会创建一个该对象，
 * 所以这里使用单例模式，保证所有线程只能创建一个ServerChannelMapping实例。
 * map中保存客户端唯一的onum与Channel映射
 */
public class ServerChannelMapping {

    private ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>(20);

    private static ServerChannelMapping mapping;

    private ServerChannelMapping() {

    }

    public static ServerChannelMapping getInstance() {
        if (mapping == null) {
            mapping = new ServerChannelMapping();
        }
        return mapping;
    }

    public void putContextIntoMap(String onum, Channel channel){
        this.map.put(onum, channel);
    }

    public Channel getChannelByNum(String onum) {
        return this.map.get(onum);
    }

    public String getONumByChannel(Channel c) {
        return map.search(1, (k, v) -> {
            if (c.id().asLongText().equals(v.id().asLongText())) {
                return k;
            } else {
                return null;
            }
        });
    }
}
