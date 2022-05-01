package com.example.alter.altermethod;


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
@Hook(value = "com.example.shuttledemo.service.SpringService")
public class SpringServiceHook {

    private static final Log logger = LogFactory.getLog(SpringServiceHook.class);

    @Before(value = {"test(String)"})
    public void before(Holder<String> param, @Proxy Object proxiedInstance) {
        logger.info("SpringService 获取到的注入的示例"+proxiedInstance);
        logger.info("SpringService test方法的参数:param:" + param.get());
        param.set("参数被修改了");
        logger.info("SpringService test方法的参数被修改为:param:" + param.get());
    }

    @After(value = {"test(String)"})
    public void after(@Returned Holder<String> result, @Proxy Object proxiedInstance) {
        logger.info("SpringService 获取到的注入的示例"+proxiedInstance);
        result.set("如果你看到这个，说明SpringService test方法的结果已经被修改了");
    }

}
