package io.serial.rpc;

/**
 * @program: therpc
 * @description: 序列化协议
 * @author: sawyer
 * @create: 2020-07-04 15:56
 **/
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result; // 返回值

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

//    @Override
//    public String toString() {
//        return "RpcResponse{" +
//                "requestId='" + requestId + '\'' +
//                ", error=" + error +
//                ", result=" + result +
//                '}';
//    }
}