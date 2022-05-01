package me.ezerror.shuttle.util;

import java.util.HashMap;


public class ShuttleInstanceKeeper {

    private static final ThreadLocal<HashMap<Class<?>, Object>> conn = new ThreadLocal<>();


    /**
     * 获取实例
     *
     * @param declaringClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object getInstance(Class<?> declaringClass) throws InstantiationException, IllegalAccessException {
        if (conn.get() == null) {
            conn.set(new HashMap<>());
        }
        Object o = conn.get().get(declaringClass);
        if (o == null) {
            Object value = declaringClass.newInstance();
            conn.get().put(declaringClass, value);
            o = value;
        }
        return o;
    }
}
