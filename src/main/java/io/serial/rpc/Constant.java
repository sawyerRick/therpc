package io.serial.rpc;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:55
 **/
public interface Constant {

    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}
