package io.serial.rpc;

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
        String result = helloService.hello("World");
        ApiResp apiResp = helloService.getResp("haha", 2);
        System.out.println("result = " + result);
        System.out.println("apiResp = " + apiResp);
    }
}
