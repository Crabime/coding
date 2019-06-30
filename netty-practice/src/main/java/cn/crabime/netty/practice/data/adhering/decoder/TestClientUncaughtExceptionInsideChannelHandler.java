package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 测试在netty中消息IO异常时的捕获
 */
public class TestClientUncaughtExceptionInsideChannelHandler extends ChannelHandlerAdapter {
    private final Logger logger = Logger.getLogger(getClass().getName());

    int counter = 0;

    static final String w = "Hi Crabime, Welcome to Netty!$_";

    /**
     * 通道打开时，触发该方法，向对端发送十次消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = null;
        for (int i = 0; i < 10; i++) {
            buf = Unpooled.copiedBuffer(w.getBytes());
            ctx.writeAndFlush(buf);
        }
    }

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("The is " + ++counter + " times client receive message [" + msg + "]");
        // 这里没必要人为去创建InBound IO异常，抛出的异常可以被exceptionCaught捕获
//        throw new IOException("模拟这里IO异常");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("来到这里了!");
        cause.printStackTrace();
        ctx.close();
    }

}
