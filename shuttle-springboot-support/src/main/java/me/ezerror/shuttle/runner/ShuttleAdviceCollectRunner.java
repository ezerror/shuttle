package me.ezerror.shuttle.runner;

import me.ezerror.shuttle.annotation.After;
import me.ezerror.shuttle.annotation.Before;
import me.ezerror.shuttle.annotation.Hook;
import me.ezerror.shuttle.aware.ShuttleAdviceContainer;
import me.ezerror.shuttle.entity.ShuttleAdvice;
import me.ezerror.shuttle.entity.ShuttleAdvices;
import me.ezerror.shuttle.inter.ShuttlePathConverter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：shiyuan
 * @date ：Created in 2022/4/30 23:52
 * @description：
 * @version:
 */
@Component
@Order(1)
public class ShuttleAdviceCollectRunner implements ApplicationRunner {
    @Value("${shuttle.scanPackage:com.example}")
    private String scanPackage;
    
    @Autowired(required = false)
    private ShuttlePathConverter shuttlePathConverter;


    @Override
    public void run(ApplicationArguments args) {
        // 1、扫描所有的通知
        List<ShuttleAdvice> pathMethodMapping = scanShuttleAdvice();
        // 2、映射
        // map structure {proxiedPath -> before/after -> advice_methods}
        Map<String, Map<String, ShuttleAdvices>> mapping = new HashMap<>();
        
        Map<String, ShuttleAdvices> pathForAdvices = pathMethodMapping.stream().collect(Collectors.groupingBy(ShuttleAdvice::getProxiedFullPath))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> ShuttleAdvices.fromList(e.getValue())));


//        Map<String, List<ShuttleAdvice>> pathForAdvices = pathMethodMapping.stream().collect(Collectors.groupingBy(ShuttleAdvice::getProxiedFullPath));
        for (Map.Entry<String, ShuttleAdvices> entry : pathForAdvices.entrySet()) {
            Map<String, ShuttleAdvices> temp = entry.getValue().stream().collect(Collectors.groupingBy(ShuttleAdvice::getAdviceLocation)).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> ShuttleAdvices.fromList(e.getValue())));;
            mapping.put(entry.getKey(), temp);
        }
        ShuttleAdviceContainer.shuttleAdviceMap.putAll(mapping);
        // 3、类路径映射
        ShuttleAdviceContainer.proxiedClassPathList.addAll(pathMethodMapping.stream().map(ShuttleAdvice::getProxiedClassPath).collect(Collectors.toList()));
    }

    private List<ShuttleAdvice> scanShuttleAdvice() {
        List<ShuttleAdvice> pathMethodMapping = new ArrayList<>();
        Reflections reflections = new Reflections(scanPackage);
        //获取带Hook注解的类
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Hook.class);
        for (Class<?> clazz : classes) {
            Hook hook = clazz.getAnnotation(Hook.class);
            String separator = hook.value().contains("/") ? "/" : "#";
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Before before = method.getAnnotation(Before.class);
                After after = method.getAnnotation(After.class);
                if (after != null) {
                    for (String proxiedMethod : after.value()) {
                        ShuttleAdvice advice = new ShuttleAdvice();
                        String value = hook.value() + separator + proxiedMethod;
                        if (shuttlePathConverter != null) {
                            value = shuttlePathConverter.mappingPath(value);
                        }
                        advice.setProxiedClassPath(value.split(separator)[0]);
                        advice.setProxiedFullPath(value);
                        advice.setAdviceType("local");
                        advice.setAdviceLocation("after");
                        advice.setExecutorMethod(method);
                        pathMethodMapping.add(advice);
                    }
                }
                if (before != null) {
                    for (String proxiedMethod : before.value()) {
                        String value = hook.value() + separator + proxiedMethod;
                        if (shuttlePathConverter != null) {
                            value = shuttlePathConverter.mappingPath(value);
                        }
                        ShuttleAdvice advice = new ShuttleAdvice();
                        advice.setProxiedClassPath(value.split(separator)[0]);
                        advice.setProxiedFullPath(value);
                        advice.setAdviceType("local");
                        advice.setAdviceLocation("before");
                        advice.setExecutorMethod(method);
                        pathMethodMapping.add(advice);
                    }
                }
            }
        }
        return pathMethodMapping;
    }
}
