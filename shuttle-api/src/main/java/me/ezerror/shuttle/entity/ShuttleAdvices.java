package me.ezerror.shuttle.entity;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：shiyuan
 * @date ：Created in 2022/5/2 3:23
 * @description：
 * @version:
 */
public class ShuttleAdvices extends ArrayList<ShuttleAdvice> {

    public static ShuttleAdvices fromList(Collection<ShuttleAdvice> list) {
        ShuttleAdvices shuttleAdvices = new ShuttleAdvices();
        shuttleAdvices.addAll(list);
        return shuttleAdvices;
    }

    public List<Method> getMethodsByType(String type) {
        Map<String, List<Method>> typeAndMethods = stream().collect(Collectors
                .groupingBy(ShuttleAdvice::getAdviceLocation, Collectors.mapping(ShuttleAdvice::getExecutorMethod, Collectors.toList())));
        return typeAndMethods.getOrDefault(type,new ArrayList<>());
    }
}
