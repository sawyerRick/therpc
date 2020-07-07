package io.serial.rpc.config;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 15:29
 **/
public abstract class RpcConsumerConfig {

    ConsumerRegistry registry = new ConsumerRegistry();

    static class ConsumerRegistry {

    }

    protected void addConsumer(ConsumerRegistry registry){};
}
