package io.serial.rpc.client;

import io.serial.rpc.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 21:21
 **/
public class ClientBootstrap {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        HelloService service = context.getBean(HelloService.class);
        System.out.println("service.hello(\"sawyer\") = " + service.hello("sawyer"));
    }
}
