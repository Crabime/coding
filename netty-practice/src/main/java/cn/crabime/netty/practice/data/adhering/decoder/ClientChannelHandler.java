package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * 测试在netty中消息IO异常时的捕获
 */
public class ClientChannelHandler extends ChannelHandlerAdapter {
    private final Logger logger = Logger.getLogger(getClass().getName());

    int counter = 0;

    /**
     * 通道打开时，触发该方法，向对端发送十次消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        new Thread(() -> {
                System.out.println("可以开始输入您想要的内容了：");
                Channel channel = ctx.channel();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String in = null;
                try {
                    in = br.readLine();
                    while (!in.equals("再见")) {
                        channel.writeAndFlush(Unpooled.copiedBuffer(in.getBytes()));
                        in = br.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    channel.writeAndFlush(in);
                    channel.close();
                }
            }).start();

    }

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString().replace(System.getProperty("line.separator"), "");
        logger.info("The is " + ++counter + " times client receive message [" + message + "]");
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
