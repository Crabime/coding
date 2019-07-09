package cn.crabime.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

public class ServerChannelHandler extends ChannelHandlerAdapter {

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatMessage message = (ChatMessage) msg;
        switch (message.getType()) {
            case MyConstant.AUTH_MES:
                UserMapping mapping = new UserMapping();
                Channel channel = ctx.channel();
                String oNum = message.getMessage();
                mapping.setChannel(channel);
                mapping.setOnum(oNum);
                ServerChannelMapping.getInstance().putContextIntoMap(oNum, mapping);
                // 触发系统通知那些有发离线消息用户
                CopyOnWriteArraySet<UserMapping> waitingList = ServerChannelMapping.getInstance().toggleOnlineNotice(oNum);
                for (UserMapping userMapping : waitingList) {
                    userMapping.getChannel().writeAndFlush(userMapping.getOnum() + "说：我上线了");
                }
                logger.info(message.getMessage() + "成功连接！");
                break;
            case MyConstant.NORMAL_MES:
                logger.info("发送消息为：" + message.getMessage());

                // 获取当前用户UserMapping，将离线用户列表添加到集合中
                Channel c = ctx.channel();
                UserMapping um = ServerChannelMapping.getInstance().getUserMappingByChannel(c);
                List<String> offlineList = um.getOfflineFriends();
                String toNum = message.getToNum(), mes = message.getMessage();
                Channel toChannel = ServerChannelMapping.getInstance().getChannelByNum(toNum);
                // 对方不在线
                if (toChannel == null) {
                    if (offlineList == null) {
                        offlineList = new ArrayList<>();
                    }
                    offlineList.add(toNum);
                    um.setOfflineFriends(offlineList);
                    ctx.writeAndFlush("系统消息：" + toNum + "目前不在线...");
                } else {
                    // 获取到当前channel的onum
                    String onum = ServerChannelMapping.getInstance().getONumByChannel(ctx.channel());
                    toChannel.writeAndFlush(onum + "说：" + mes);
                }
                break;
            default:
                logger.info("未知的消息类型");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
