package me.ezerror.shuttle.aware;

import me.ezerror.shuttle.entity.ShuttleAdvices;
import me.ezerror.shuttle.util.MethodUtil;
import me.ezerror.shuttle.util.ShuttleRegexMap;
import me.ezerror.shuttle.util.ShuttleRegexSet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ShuttleAdviceContainer {


    public static final Map<String, Map<String, ShuttleAdvices>> shuttleAdviceMap = new ShuttleRegexMap<>();

    public static final Set<String> proxiedClassPathList = new ShuttleRegexSet();

    public static final List<Method> proxiedMethodList = new ArrayList<>();


    /**
     * 方法是否受代理
     *
     * @param method
     * @return
     */
    public static boolean isMethodInvoked(Method method) {
        String methodFullPath = MethodUtil.getPathWithSimpleArgs(method);
        return shuttleAdviceMap.containsKey(methodFullPath);
    }

    /**
     * 类是否受代理
     *
     * @param className
     * @return
     */
    public static boolean isClassInvoked(String className) {
        return proxiedClassPathList.contains(className);
    }

    /**
     * 根据受代理类路径和代理时机，获取通知执行类
     *
     * @param invocationPath
     * @param type
     * @return
     */
    public static List<Method> getMethods(String invocationPath, String type) {
        ShuttleAdvices shuttleAdvices = shuttleAdviceMap.get(invocationPath).get(type);
        return shuttleAdvices.getMethodsByType(type);
    }
}
