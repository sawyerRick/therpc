package io.serial.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:56
 **/
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            // TODO 学习一下 protostuff，Java 版本的 protobuf
            byte[] data = SerializationUtil.serialize(in);
            // 解决拆包粘包问题，数据格式：len(32 bit)data(len bit)
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
