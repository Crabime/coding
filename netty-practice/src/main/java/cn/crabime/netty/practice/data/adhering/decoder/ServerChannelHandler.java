package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Vector;
import java.util.logging.Logger;

public class ServerChannelHandler extends ChannelHandlerAdapter {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private Vector<ChannelHandlerContext> vector = new Vector<>();

    int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        vector.add(ctx);
        String onum = ctx.attr(AttributeKey.valueOf("onum")).get().toString();
        logger.info("当前onum为：" + onum);
        super.channelActive(ctx);
    }

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        logger.info("当前channel ID为：" + ctx.channel().id().asLongText() + "连接的号码为：" + ctx.channel().attr(AttributeKey.valueOf("onum")).get());
        String body = (String) msg;
        logger.info("The is " + ++counter + " times server receive message [" + body + "]");
        body += System.getProperty("line.separator");
        ByteBuf byteBuf = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
