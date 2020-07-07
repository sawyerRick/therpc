package io.serial.rpc.conn;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.serial.rpc.RpcResponse;

import java.util.concurrent.Semaphore;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-07 14:13
 **/
public class ConsumerHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private final Semaphore sendLock;

    ConsumerNettyConn conn;

    public ConsumerHandler(ConsumerNettyConn conn, Semaphore sendLock) {
        this.conn = conn;
        this.sendLock = sendLock;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse resp) throws Exception {
        conn.setResp(resp);
        sendLock.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
