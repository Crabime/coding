package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
                ServerChannelMapping.getInstance().putContextIntoMap(message.getMessage(), ctx.channel());
                logger.info(message.getMessage() + "成功连接！");
                break;
            case MyConstant.NORMAL_MES:
                logger.info("发送消息为：" + message.getMessage());
                String toNum = message.getToNum(), mes = message.getMessage();

                Channel toChannel = ServerChannelMapping.getInstance().getChannelByNum(toNum);
                // 对方不在线
                if (toChannel == null) {
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
