package me.ezerror.shuttle.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.ezerror.shuttle.util.MethodUtil;
import me.ezerror.shuttle.util.ParameterUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 封装过程参数工厂
 *
 * @author sy
 * @date 2021/11/22
 **/
public class RunnableMethodParamFactory {

    public static RunnableMethodParam getParam(Method invocationMethod, Object[] invocationArguments) {
        String invocationPath = MethodUtil.getPathWithSimpleArgs(invocationMethod);
        Parameter[] parameters = invocationMethod.getParameters();
        String[] p = new String[parameters.length];
        Boolean[] bp = new Boolean[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            p[i] = parameters[i].getParameterizedType().getTypeName();
            bp[i] = ParameterUtil.isParam(parameters[i]);
        }
        return new RunnableMethodParam(invocationPath, p, invocationArguments, bp);
    }

    public static RunnableMethodParam getParam(Method invocationMethod) {
        return getParam(invocationMethod, null);
    }

    public static RunnableMethodParam parse(JSONObject methodParam) {
        JSONArray invocationArguments = methodParam.getJSONArray("invocationArguments");
        String invocationPath = methodParam.getString("invocationPath");
        JSONArray parameters = methodParam.getJSONArray("parameters");
        JSONArray isParam = methodParam.getJSONArray("isParam");

        String[] p = new String[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            p[i] = parameters.getString(i);
        }

        Object[] arguments = new String[invocationArguments.size()];
        for (int i = 0; i < invocationArguments.size(); i++) {
            arguments[i] = invocationArguments.get(i);
        }

        Boolean[] bp = new Boolean[isParam.size()];
        for (int i = 0; i < isParam.size(); i++) {
            bp[i] = isParam.getBoolean(i);
        }
        return new RunnableMethodParam(invocationPath, p, arguments, bp);
    }
}
