package com.example.alter.feature;

import me.ezerror.shuttle.annotation.*;
import me.ezerror.shuttle.inter.Holder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 使用Skip注解声明不执行原始方法
 * 在这种情况下，前置注解只能修改参数其实不会对最终结果有任何影响了
 * 在前置通知中，只能进行一些监控性质或者日志记录的操作了
 *
 * @author sy
 * @date 2021/11/4
 **/
@Hook(value = "com.example.shuttledemo.service")
public class SkipServiceHook {

    private static final Log logger = LogFactory.getLog(SkipServiceHook.class);

    @Before(value = {"test(String)"})
    @Skip
    public void before(Holder<String> param) {
        logger.info("SkipService test方法的参数:param:" + param.get());
        param.set("参数被修改了");
        logger.info("SkipService test方法的参数被修改为:param:" + param.get());
    }

    @After(value = {"test(String)"})
    @Skip
    public void after(@Returned Holder<String> result) {
        logger.info("如果日志中没有SkipService的日志输出，说明成功跳过了原始方法执行，方法结果完全是由后置通知提供的");
        if (result.get() == null) {
            result.set("SkipService:如果结果是这个，说明成功跳过了原始方法,结果完全由后置通知提供");
        }
    }

}
