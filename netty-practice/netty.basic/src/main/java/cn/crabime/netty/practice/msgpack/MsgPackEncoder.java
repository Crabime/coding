package cn.crabime.netty.practice.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack msgPack = new MessagePack();

        // 使用MessagePack框架对msg消息进行编码
        byte[] bytes = msgPack.write(msg);

        // 写入到netty字节缓冲区中
        out.writeBytes(bytes);
    }
}
