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
import io.netty.handler.logging.LoggingHandler;

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
                            socketChannel.pipeline().addLast(new LoggingHandler());
                            // StringDecoder将ByteBuf对象转换为字符串
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TestClientUncaughtExceptionInsideChannelHandler());
                        }
                    });
            // 异步连接，同步等待
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

}
