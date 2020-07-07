package io.serial.rpc;

import io.serial.rpc.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 16:01
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client.xml")
public class HelloServiceTest {

    @Autowired
    HelloService helloService;

    @Test
    public void helloTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            helloService.hello("hello:" + i);
        }
        System.out.println("time:" + (System.currentTimeMillis() - start));
    }
}
