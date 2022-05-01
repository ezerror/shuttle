package me.ezerror.shuttle.util;

import me.ezerror.shuttle.annotation.Proxy;
import me.ezerror.shuttle.annotation.Returned;
import me.ezerror.shuttle.annotation.Thrown;
import me.ezerror.shuttle.aware.ShuttleAdviceContainer;
import me.ezerror.shuttle.entity.RunnableMethodParam;
import me.ezerror.shuttle.entity.RunnableMethodParamFactory;
import me.ezerror.shuttle.inter.Holder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：shiyuan
 * @date ：Created in 2022/5/2 3:19
 * @description：
 * @version:
 */
public class RunnableMethodUtil {


    public static void invokeBeforeMethod(RunnableMethodParam methodParam) {
        List<Method> methods = ShuttleAdviceContainer.getMethods(methodParam.invocationPath, "before");
        if (methods == null) {
            return;
        }
        for (int i = 0; i < methods.size(); i++) {
            Method beforeMethod = methods.get(i);
            Object[] arguments = new Object[beforeMethod.getParameters().length];
            injectOtherMethodParameter(null, null, beforeMethod.getParameters(), arguments, methodParam.getProxy());
            injectMethodParameter(beforeMethod, arguments, methodParam);
            try {
                final Object[] cloneParameter = arguments.clone();
                beforeMethod.invoke(ShuttleInstanceKeeper.getInstance(beforeMethod.getDeclaringClass()), cloneParameter);
                arguments = cloneParameter;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            // 实际参数修改
            modifyActualMethodParameter(RunnableMethodParamFactory.getParam(beforeMethod), arguments, methodParam);
        }
    }



    
    public static Object invokeAfterMethod(Object result, Throwable exception, RunnableMethodParam shuttleMethodParam) {
        // 参数 按类型顺位
        List<Method> methods = ShuttleAdviceContainer.getMethods(shuttleMethodParam.invocationPath, "after");
        if (methods == null) {
            return result;
        }
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            Parameter[] parameters = method.getParameters();
            Object[] arguments = new Object[parameters.length];
            // 注入参数
            RunnableMethodUtil.injectMethodParameter(method, arguments, shuttleMethodParam);
            // 注入结果、异常
            int returnedIndex = injectOtherMethodParameter(result, exception, parameters, arguments, shuttleMethodParam.getProxy());
            try {
                final Object[] cloneParameter = arguments.clone();
                method.invoke(ShuttleInstanceKeeper.getInstance(method.getDeclaringClass()), cloneParameter);
                arguments = cloneParameter;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 结果重载
            if (returnedIndex >= 0) {
                result = ((Holder<?>) arguments[returnedIndex]).get();
            }
            // 实际参数修改
            RunnableMethodUtil.modifyActualMethodParameter(RunnableMethodParamFactory.getParam(method), arguments, shuttleMethodParam);
        }
        return result;
    }



    /**
     * 注入后置通知参数
     *
     * @param result
     * @param exception       异常
     * @param parameters      方法参数
     * @param methodArguments methodArguments
     * @return 结果参数所在的index
     */
    private static int injectOtherMethodParameter(Object result, Throwable exception, Parameter[] parameters, Object[] methodArguments, Object proxy) {
        // 记录返回值位置
        int returnedIndex = -1;
        for (int i = 0; i < parameters.length; i++) {
            // 若为returned
            if (parameters[i].isAnnotationPresent(Returned.class)) {
                methodArguments[i] = new Holder<>(result);
                returnedIndex = i;
            }

            // 若为异常
            if (parameters[i].isAnnotationPresent(Thrown.class)) {
                methodArguments[i] = exception;
            }

            // 若为代理类实例
            if (parameters[i].isAnnotationPresent(Proxy.class)) {
                methodArguments[i] = proxy;
            }
        }
        return returnedIndex;
    }


    /**
     * 注入参数
     *
     * @param method
     */
    public static void injectMethodParameter(Method method, Object[] methodArguments, RunnableMethodParam shuttleMethodParam) {
        Object[] invocationArguments = shuttleMethodParam.invocationArguments;
        Parameter[] parameters = method.getParameters();
        HashMap<String, Integer> classIndexMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            if (ParameterUtil.isParam(parameter)) {
                String proxyMethodParam = parameter.getParameterizedType().getTypeName();
                boolean isNeedHolder = proxyMethodParam.startsWith(Holder.class.getTypeName());
                if (isNeedHolder) {
                    proxyMethodParam = proxyMethodParam.replace(Holder.class.getTypeName() + "<", "");
                    proxyMethodParam = proxyMethodParam.substring(0, proxyMethodParam.length() - 1);
                }
                List<Object> objects = new InvocationUtil().mappingClassToObject(shuttleMethodParam, invocationArguments).get(proxyMethodParam);
                Integer index = classIndexMap.get(proxyMethodParam);
                if (index == null) {
                    index = 0;
                }
                if (objects == null || objects.size() <= index) {
                    throw new RuntimeException("Parameter does not matched");
                }
                methodArguments[i] = objects.get(index);
                if (isNeedHolder) {
                    methodArguments[i] = new Holder<>(objects.get(index));
                }
                classIndexMap.put(proxyMethodParam, ++index);
            }
        }
    }



    /**
     * 修改实际参数
     *
     * @param methodArguments
     */
    public static void modifyActualMethodParameter(RunnableMethodParam methodParam, Object[] methodArguments, RunnableMethodParam shuttleMethodParm) {
        final Map<String, List<Object>> map = new InvocationUtil().mappingClassToObject(methodParam, methodArguments, true);
        final String[] invocationMethodParameters = shuttleMethodParm.parameters;
        HashMap<String, Integer> classIndexMap = new HashMap<>();
        for (int i = 0; i < invocationMethodParameters.length; i++) {
            String typeName = invocationMethodParameters[i];
            List<Object> objects = map.get(typeName);
            if (objects != null) {
                Integer index = classIndexMap.get(typeName);
                if (index == null) {
                    index = 0;
                }
                if (objects.get(index) != null) {
                    shuttleMethodParm.invocationArguments[i] = objects.get(index);
                    classIndexMap.put(typeName, ++index);
                }
            }
        }
    }
}
