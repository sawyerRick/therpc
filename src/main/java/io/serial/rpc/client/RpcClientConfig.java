package io.serial.rpc.client;

import io.serial.rpc.RpcConsumerConfig;
import io.serial.rpc.RpcProxy;
import io.serial.rpc.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 15:24
 **/
@Configuration
public class RpcClientConfig extends RpcConsumerConfig {

    @Autowired
    RpcProxy rpcProxy;

    @Bean
    HelloService helloService() {
        return rpcProxy.create(HelloService.class);
    }
}
