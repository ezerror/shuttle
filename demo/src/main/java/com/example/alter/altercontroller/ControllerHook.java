package com.example.alter.altercontroller;


import me.ezerror.shuttle.annotation.*;
import me.ezerror.shuttle.inter.Holder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 示例二开代码
 *
 * @author sy
 * @date 2021/11/4
 **/
@Hook(value = "/test")
public class ControllerHook {

    private static final Log logger = LogFactory.getLog(ControllerHook.class);


    @Before(value = {"alterController"})
    public void before(@Param Holder<String> param) {
        logger.info("alterController用户请求的参数为:param:" + param.get());
        param.set("参数被修改了");
        logger.info("alterController 用户请求的参数被修改为:param:" + param.get());
    }

    @After(value = {"alterController"})
    public void after(@Returned Holder<String> result) {
        result.set("如果你看到这个，说明这个接口的结果已经被修改了");
    }

}
