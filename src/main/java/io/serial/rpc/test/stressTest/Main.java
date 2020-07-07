package io.serial.rpc.test.stressTest;

import io.serial.rpc.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-07 17:07
 **/
public class Main {

    final static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        int coreCount = 12;
        CountDownLatch latch = new CountDownLatch(coreCount);
        final ExecutorService executor = Executors.newFixedThreadPool(coreCount);
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        HelloService service = context.getBean(HelloService.class);
        long startTime = System.currentTimeMillis();
        for(int i = 0 ; i < coreCount ; i++){
            executor.execute(() -> {
                while(counter.incrementAndGet() < 100000){
                    try {
                        service.hello("h");
                    } catch (Exception e) {
                    }
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        long tps = counter.get() / (endTime - startTime) * 1000;
        System.out.println("tps = " + tps); // 60k tps
        System.exit(0);
    }
}
