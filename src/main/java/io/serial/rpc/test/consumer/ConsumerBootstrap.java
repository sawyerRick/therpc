package io.serial.rpc.test.consumer;

import io.serial.rpc.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 21:21
 **/
public class ConsumerBootstrap {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        HelloService service = context.getBean(HelloService.class);
        System.out.println("hello:" + service.hello("sawyer"));
        System.exit(0);
    }
}
