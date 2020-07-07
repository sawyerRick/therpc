package io.serial.rpc.conn;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.serial.rpc.*;
import io.serial.rpc.codec.RpcDecoder;
import io.serial.rpc.codec.RpcEncoder;

import javax.xml.ws.Response;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-07 14:03
 **/
public class ConsumerNettyConn implements ConsumerConn {

    private Bootstrap bootstrap = new Bootstrap();

    // remoteAddr 格式：InetSocketAddress.toString()
//    private final Map<String /* remoteAddr */, Channel> connInfo = new ConcurrentHashMap<>();

    private InetSocketAddress inetAddr;

    private Channel channel;

    private final long timeout = 2000;

    private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    private volatile RpcResponse resp;

    // 用于同步 send 和 read0
    private final Semaphore sendLock = new Semaphore(0);

    public ConsumerNettyConn(String host, int port) {
        inetAddr = new InetSocketAddress(host, port);
        init();
    }

    @Override
    public void init() {
        try {
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new RpcDecoder(RpcResponse.class))
                                    .addLast(new RpcEncoder())
                                    .addLast(new ConsumerHandler(ConsumerNettyConn.this, sendLock));
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true); // 长连接
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void connect() {
        try {
            bootstrap.connect(inetAddr).sync().addListener(((ChannelFutureListener) future -> {
//                Channel ch = future.channel();
//                connInfo.put(ch.remoteAddress().toString(), ch);

                ConsumerNettyConn.this.channel = future.channel();
            }));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RpcResponse send(RpcRequest req) {

//        Channel ch = connInfo.get(inetAddr.toString());
        if (channel == null) {
            System.out.println("连接已关闭");
            return null;
        }

        try {
            channel.writeAndFlush(req).sync();
//            System.out.println("consumer: send to remote " + channel.remoteAddress());
            if (!sendLock.tryAcquire(timeout, timeUnit)) {
                throw new RuntimeException("rpc 请求超时:" + req + " " + channel.remoteAddress());
            }
            return resp;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {

    }

    public RpcResponse getResp() {
        return resp;
    }

    public void setResp(RpcResponse resp) {
        this.resp = resp;
    }
}
