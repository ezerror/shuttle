package me.ezerror.shuttle.util;

import me.ezerror.shuttle.entity.RunnableMethodParam;
import me.ezerror.shuttle.inter.Holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class InvocationUtil {

    /**
     * 映射缓存
     */
    private final HashMap<String, Map<String, List<Object>>> mappingCache = new HashMap<>();

    public Map<String, List<Object>> mappingClassToObject(RunnableMethodParam shuttleMethodParam, Object[] arguments) {
        return mappingClassToObject(shuttleMethodParam, arguments, false);
    }

    public Map<String, List<Object>> mappingClassToObject(RunnableMethodParam shuttleMethodParam, Object[] arguments, boolean onlyHolder) {
        Map<String, List<Object>> data;
        String path = shuttleMethodParam.invocationPath;
        if ((data = mappingCache.get(path)) != null) {
            return data;
        }
        List<ShuttleRunnableParameter> shuttleRunnableParameters = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            String typeName = shuttleMethodParam.parameters[i];
            if (shuttleMethodParam.isParam[i]) {
                if (arguments[i] instanceof Holder) {
                    typeName = typeName.replace(Holder.class.getName() + "<", "");
                    typeName = typeName.substring(0, typeName.length() - 1);
                    shuttleRunnableParameters.add(new ShuttleRunnableParameter(typeName, ((Holder<?>) arguments[i]).get()));
                }
                else if (!onlyHolder) {
                    shuttleRunnableParameters.add(new ShuttleRunnableParameter(typeName, arguments[i]));
                }
            }
            else if (!onlyHolder) {
                shuttleRunnableParameters.add(new ShuttleRunnableParameter(typeName, arguments[i]));
            }

        }
        data = shuttleRunnableParameters.stream().collect(Collectors.groupingBy(ShuttleRunnableParameter::getTypeName, Collectors.mapping(ShuttleRunnableParameter::getObject, Collectors.toList())));
        mappingCache.put(path, data);
        return data;
    }


    /**
     * 封装参数类型及其本身，主要应用于泛型类的判断，如List、Map、Set等
     */
    static protected class ShuttleRunnableParameter {
        private final String type;
        private final Object object;

        public ShuttleRunnableParameter(String type, Object object) {
            this.type = type;
            this.object = object;
        }

        public String getTypeName() {
            return type;
        }

        public Object getObject() {
            return object;
        }
    }
}
