package me.ezerror.shuttle.util;

import me.ezerror.shuttle.annotation.Param;

import java.lang.reflect.Parameter;

/**
 * 参数工具类
 * @author sy
 * @date 2021/11/22
 **/
public class ParameterUtil {
    public static boolean isParam(Parameter parameter) {
        return parameter.getAnnotations().length == 0 || parameter.isAnnotationPresent(Param.class);
    }
}
