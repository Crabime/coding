package cn.crabime.netty.practice.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 消息解码
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 获取缓冲区中可获取的字节长度
        int readableBytes = msg.readableBytes();
        byte[] buffer = new byte[readableBytes];

        // 将msg缓冲区中数据转移到buffer字节数组中
        // readerIndex方法返回缓冲区中开始读的字节位置
        msg.getBytes(msg.readerIndex(), buffer, 0, readableBytes);

        MessagePack pack = new MessagePack();
        out.add(pack.read(buffer));
    }
}
