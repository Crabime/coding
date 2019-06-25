package cn.crabime.netty.practice.data.adhering;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyTimerClient {

    public void connect(String ip, int port) {
        // 创建NioEventLoopGroup线程组
        EventLoopGroup group = new NioEventLoopGroup();

        // 客户端辅助启动类
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        // 在NioSocketChannel进行初始化时，将ChannelHandler设置到ChannelPipeline中
                        ch.pipeline().addLast(new NettyTimerClientHandler());
                    }
                });

        try {
            // 调用connect发起异步连接，然后调用同步方法等待连接成功
            ChannelFuture f = b.connect(ip, port).sync();

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        new NettyTimerClient().connect("127.0.0.1", port);
    }

    private class NettyTimerClientHandler extends ChannelHandlerAdapter {

        private int counter;

        private byte[] req;

        public NettyTimerClientHandler() {
            req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        }

        /**
         * 当客户端与服务端进行TCP连接链路建立成功时，Netty NIO线程会调用该方法
         * 发送查询时间指令到服务端
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf message = null;
            for (int i = 0; i < 100; i++) {
                message = Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        /**
         * 服务端返回应答消息时，该方法会被调用，从Netty的ByteBuf中读取消息
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 这里netty已经对消息进行解码了
            String message = (String) msg;
            System.out.println("Now is : " + message + ", the counter is : " + ++counter);
        }

        /**
         * 发生异常时，释放资源
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
