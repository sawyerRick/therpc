package io.serial.rpc.server;

import io.serial.rpc.RpcService;
import io.serial.rpc.ApiResp;
import io.serial.rpc.HelloService;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:49
 **/
@RpcService(value = HelloService.class, alias = "hello") // 指定远程接口
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public ApiResp getResp(String id, Integer num) {

        ApiResp resp = new ApiResp();
        resp.setCode("this is the code:" + id);
        resp.setData("this is the data:" + num);

        return resp;
    }
}