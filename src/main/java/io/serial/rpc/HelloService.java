package io.serial.rpc;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:49
 **/
public interface HelloService {

    String hello(String name);

    ApiResp getResp(String id, Integer num);
}