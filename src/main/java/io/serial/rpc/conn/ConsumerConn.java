package io.serial.rpc.conn;

import io.serial.rpc.RpcRequest;
import io.serial.rpc.RpcResponse;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-07 14:01
 **/
public interface ConsumerConn {
    void init();

    void connect();

    RpcResponse send(RpcRequest req);

    void close();
}
