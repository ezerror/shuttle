package me.ezerror.shuttle.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodUtil {


    /**
     * 获取的方法全路径（简化带参）
     *
     * @param method method
     * @return 方法全路径（简化带参）
     */
    public static String getPathWithSimpleArgs(Method method) {
        final StringBuilder path = new StringBuilder(method.getDeclaringClass().getName()).append("#").append(method.getName())
                .append("(");
        final Type[] types = method.getGenericParameterTypes();
        String[] temp = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            final Type type = types[i];
            String[] typeNames = type.getTypeName().split("<");
            for (int j = 0; j < typeNames.length; j++) {
                if (typeNames[j].contains(",")) {
                    String[] deep = typeNames[j].split(",");
                    for (int h = 0; h < deep.length; h++) {
                        deep[h] = deep[h].substring(deep[h].lastIndexOf('.') + 1);
                    }
                    typeNames[j] = StringUtils.join(deep, ",");
                }
                else {
                    typeNames[j] = typeNames[j].substring(typeNames[j].lastIndexOf('.') + 1);
                }
            }
            temp[i] = StringUtils.join(typeNames, "<");
        }
        path.append(StringUtils.join(temp, ","));
        path.append(")");
        return path.toString();
    }
}
