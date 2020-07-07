package io.serial.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-04 15:57
 **/
public class ProviderHandler extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderHandler.class);

    private final Map<String /* interfaceName */, Object /* serviceBean */> serviceMap;

    public ProviderHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("provider channel active:" + ctx.channel().localAddress());
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, Object request) throws Exception {
        RpcRequest req = (RpcRequest) request;
        RpcResponse response = new RpcResponse();
        response.setRequestId(req.getRequestId());
        try {
            Object result = handle(req);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        try {
            ctx.writeAndFlush(response).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object handle(RpcRequest request) throws Throwable {
        Object serviceBean = serviceMap.get(request.getInterfaceName());

        if (serviceBean == null) {
            return null;
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}