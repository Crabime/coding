package cn.crabime.netty.practice.data.adhering.decoder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import static cn.crabime.netty.practice.data.adhering.decoder.MyConstant.AUTH_MES;

/**
 * 测试在netty中消息IO异常时的捕获
 */
public class ClientChannelHandler extends ChannelHandlerAdapter {
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * 通道打开时，触发该方法，向对端发送十次消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("你的登录号码：");
        BufferedReader initReader = new BufferedReader(new InputStreamReader(System.in));
        String num = initReader.readLine();
        if (num == null || "".equals(num)) {
            throw new IllegalArgumentException("必须输入你的分享号码");
        }
        ChatMessage authMes = new ChatMessage();
        authMes.setType(AUTH_MES);
        authMes.setMessage(num);
        // 为什么这个地方执行结束了，服务端没有触发channelRead事件呢？
        ctx.writeAndFlush(authMes);

        new Thread(() -> {
                System.out.print("开始聊天了，请输入你要私聊的onum：");
                ChatMessage message = new ChatMessage();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String onum = null;
                try {
                    onum = br.readLine();
                    // 设置要聊天的onum
                    message.setToNum(onum);
                    System.out.println("开始聊天吧：");
                    String doneMessage = br.readLine();
                    while (doneMessage != null && !doneMessage.equals("bye")) {
                        message.setMessage(doneMessage);
                        message.setType(MyConstant.NORMAL_MES);
                        ctx.writeAndFlush(message);
                        doneMessage = br.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ctx.close();
                }
            }).start();
    }

    /**
     * 从对端收到数据包时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString();
        System.out.println(message);
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
