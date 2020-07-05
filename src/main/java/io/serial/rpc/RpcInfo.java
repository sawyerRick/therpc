package io.serial.rpc;

/**
 * @program: therpc
 * @description:
 * @author: sawyer
 * @create: 2020-07-05 14:59
 **/
public class RpcInfo {

    String interfaceName;

    String alias;

    public RpcInfo() {
    }

    public RpcInfo(String interfaceName, String alias) {
        this.interfaceName = interfaceName;
        this.alias = alias;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpcInfo that = (RpcInfo) o;

        if (interfaceName != null ? !interfaceName.equals(that.interfaceName) : that.interfaceName != null) return false;
        return alias != null ? alias.equals(that.alias) : that.alias == null;
    }

    @Override
    public int hashCode() {
        int result = interfaceName != null ? interfaceName.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RpcServiceInfo{" +
                "className='" + interfaceName + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
