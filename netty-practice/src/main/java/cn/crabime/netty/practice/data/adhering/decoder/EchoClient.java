package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {

    public void connect(String ip, int port) {
        EventLoopGroup g = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(g).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiterBuf = Unpooled.copiedBuffer("$_".getBytes());
                            // 对消息进行特殊字符解码，后续ChannelHandler接收到的消息就是完整的数据包
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiterBuf));

                            // StringDecoder将ByteBuf对象转换为字符串
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(ip, port).sync();

            f.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            g.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        new EchoClient().connect("127.0.0.1", port);
    }

    private class EchoClientHandler extends ChannelHandlerAdapter {
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
            System.out.println("The is " + ++counter + " times client receive message [" + msg + "]");
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
