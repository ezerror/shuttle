package me.ezerror.shuttle.core.aspect;

import me.ezerror.shuttle.entity.RunnableMethodParam;
import me.ezerror.shuttle.entity.RunnableMethodParamFactory;
import me.ezerror.shuttle.util.RunnableMethodUtil;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Method;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

public class ShuttleCoreAspect {

    @Advice.OnMethodEnter(skipOn = Advice.OnDefaultValue.class)
    public static boolean before(
            @Advice.AllArguments(readOnly = false, typing = DYNAMIC) Object[] args, @Advice.Origin Method method, @Advice.This Object proxy
    ) {
        Object[] arguments = new Object[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        System.out.println(args);
        RunnableMethodParam shuttleMethodParam = RunnableMethodParamFactory.getParam(method, arguments);
        shuttleMethodParam.setProxy(proxy);
        RunnableMethodUtil.invokeBeforeMethod(shuttleMethodParam);
        args = arguments;
        return true;
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class, inline = true)
    public static void after(
            @Advice.AllArguments(readOnly = false, typing = DYNAMIC) Object[] args, @Advice.Thrown Throwable exception, @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object returned, @Advice.Origin Method method, @Advice.This Object proxy) {
        Object[] arguments = new Object[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        RunnableMethodParam shuttleMethodParam = RunnableMethodParamFactory.getParam(method, arguments);
        shuttleMethodParam.setProxy(proxy);
        returned = RunnableMethodUtil.invokeAfterMethod(returned, exception, shuttleMethodParam);
        args = arguments;
    }


}
