package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TestServerUncaughtExceptionInsideChannelHandler extends ChannelHandlerAdapter {

    private final Logger logger = Logger.getLogger(getClass().getName());

    int counter = 0;

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        logger.info("The is " + ++counter + " times server receive message [" + body + "]");
        body += "$_";
        ByteBuf byteBuf = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    // 当前handler被添加到pipeline时触发的回调事件
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        logger.info("添加handler{" + ctx.name() + "}了");
        super.handlerAdded(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
