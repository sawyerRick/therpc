package io.serial.rpc;

import io.serial.rpc.conn.ConsumerConn;
import io.serial.rpc.conn.ConsumerNettyConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-07 14:21
 **/
public class ConsumerConnManager {

    // 连接池
    private List<ConsumerConn> connList = new ArrayList<>();

    private AtomicInteger selectTimes = new AtomicInteger(0);

    public static ConsumerConnManager instance;

    public static ConsumerConnManager getInstance() {
        if (instance == null) {
            synchronized (ConsumerConnManager.class) {
                if (instance == null) {
                    instance = new ConsumerConnManager();
                }
            }
        }
        return instance;
    }

    private ConsumerConnManager() {
        initConn();
    }

    public void reconnect() {
        connList.clear();
        initConn();
    }

    private void initConn() {
        try {
            // TODO 使用 zk 发现服务
            String host = "127.0.0.1";
            int port = 8888;
            // 初始化连接数
            int num = Runtime.getRuntime().availableProcessors() / 3;
            for (int i = 0; i < num; i++) {
                connList.add(new ConsumerNettyConn(host, port));
            }
            for (ConsumerConn c : connList) {
                c.connect();
            }
        } catch (Exception e) {
            System.out.println("ConsumerConnManager ----> initConn:初始化连接失败");
            e.printStackTrace();
            connList.clear();
        }
    }

    // 负载均衡
    public ConsumerConn select() {
        int offset = selectTimes.getAndIncrement() % (connList.size() - 1);

        return connList.get(offset);
    }
}
