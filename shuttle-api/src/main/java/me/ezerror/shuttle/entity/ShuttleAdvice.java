package me.ezerror.shuttle.entity;

import java.lang.reflect.Method;

/**
 * @author ：shiyuan
 */
public class ShuttleAdvice {
    // 类型
    private String adviceType;// local or remote
    // 注入位置
    private String adviceLocation;// before or after
    // 执行方法
    private Method executorMethod;
    // 代理全路径路径
    private String proxiedFullPath;
    // 代理类路径
    private String proxiedClassPath;

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    public String getAdviceLocation() {
        return adviceLocation;
    }

    public void setAdviceLocation(String adviceLocation) {
        this.adviceLocation = adviceLocation;
    }

    public Method getExecutorMethod() {
        return executorMethod;
    }

    public void setExecutorMethod(Method executorMethod) {
        this.executorMethod = executorMethod;
    }

    public String getProxiedFullPath() {
        return proxiedFullPath;
    }

    public void setProxiedFullPath(String proxiedFullPath) {
        this.proxiedFullPath = proxiedFullPath;
    }

    public String getProxiedClassPath() {
        return proxiedClassPath;
    }

    public void setProxiedClassPath(String proxiedClassPath) {
        this.proxiedClassPath = proxiedClassPath;
    }
}
