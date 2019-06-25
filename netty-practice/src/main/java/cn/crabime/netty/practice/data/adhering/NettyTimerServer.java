package cn.crabime.netty.practice.data.adhering;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;

/**
 * 模拟TCP粘包/拆包行为
 */
public class NettyTimerServer {

    public void bind(int port) throws InterruptedException {
        // 两个线程组，包含两组NIO线程，专门用于网络事件处理
        // 用于服务端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用于进行SocketChannel的网络读写操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    // 处理网络I/O事件
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimerServerHandler());
        }
    }

    private class TimerServerHandler extends ChannelHandlerAdapter {

        private int counter;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // netty已经对发送过来的消息进行解码了
            String message = (String) msg;

            System.out.println("The time server receive order : " + message + ", the counter is : " + ++counter);

            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(message) ? new Date().toString() : "BAD ORDER";
            currentTime = currentTime + System.getProperty("line.separator");

            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.write(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;

        new NettyTimerServer().bind(port);
    }
}
