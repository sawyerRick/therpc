package io.serial.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.serial.rpc.RpcRequest;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:56
 **/
public class RpcEncoder extends MessageToByteEncoder {

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        byte[] data = SerializationUtil.serialize(in);
        // 解决拆包粘包问题，数据格式：len(32 bit)data(len bit)
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
