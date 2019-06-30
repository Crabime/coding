package cn.crabime.netty.practice.msgpack;

import cn.crabime.netty.practice.codec.UserInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

// TODO: 6/26/19 完善MessagePack在Netty中应用示例
public class EchoUsingMsgPackClient {

    public void connect(String ip, int port, int sendNumber) {
        EventLoopGroup g = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(g).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("msg decoder", new MsgPackDecoder());
                            socketChannel.pipeline().addLast("msg encoder", new MsgPackEncoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler(sendNumber));
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

        new EchoUsingMsgPackClient().connect("127.0.0.1", port, 1);
    }

    private class EchoClientHandler extends ChannelHandlerAdapter {

        int sendNumber;

        public EchoClientHandler(int sendNumber) {
            this.sendNumber = sendNumber;
        }

        /**
         * 通道打开时，触发该方法，向对端发送十次消息
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            UserInfo[] info = userInfoGen();
            for (UserInfo i : info) {
                // 将当前对象写入到Channel中
                ctx.write(i);
            }
            // 将缓冲区中内容发送出去
            ctx.flush();
        }

        private UserInfo[] userInfoGen() {
            UserInfo[] info = new UserInfo[this.sendNumber];

            UserInfo userInfo = null;
            for (int i = 0; i < sendNumber; i++) {
                userInfo = new UserInfo();
                userInfo.buildUserID(i);
                userInfo.buildUserName("ABCDEFG---->");
                info[i] = userInfo;
            }

            return info;
        }

        /**
         * 从对端收到数据包时触发该方法
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Client receive the msgpack message : " + msg);
            ctx.write(msg);
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
