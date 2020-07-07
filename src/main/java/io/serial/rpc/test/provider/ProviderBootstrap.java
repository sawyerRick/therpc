package io.serial.rpc.test.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: therpc
 * @description: rpc server 启动
 * @author: sawyer
 * @create: 2020-07-04 15:54
 **/
public class ProviderBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server.xml");
    }
}