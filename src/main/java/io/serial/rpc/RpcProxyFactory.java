package io.serial.rpc;

import io.serial.rpc.conn.ConsumerConn;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @program: therpc
 * @description: rpc 代理工厂，为消费者提供服务代理
 * @author: sawyer
 * @create: 2020-07-04 16:00
 **/
public class RpcProxyFactory {

    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxyFactory(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxyFactory(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    RpcRequest request = new RpcRequest();
                    // TODO 添加服务别名 alias
                    request.setInterfaceName(interfaceClass.getName());
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);

                    if (serviceDiscovery != null) {
                        serverAddress = serviceDiscovery.discover();
                    }

                    ConsumerConn conn = ConsumerConnManager.getInstance().select();
                    RpcResponse response = conn.send(request);

                    if (response.isError()) {
                        throw response.getError();
                    } else {
                        return response.getResult();
                    }
                }
        );
    }
}
