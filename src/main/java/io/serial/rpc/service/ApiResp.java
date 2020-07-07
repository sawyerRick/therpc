package io.serial.rpc.service;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 12:42
 **/
public class ApiResp {

    String code;

    String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResp{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
