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
@Hook("com.example.shuttledemo.service.NormalService")
public class NormalServiceHook {

    private static final Log logger = LogFactory.getLog(NormalServiceHook.class);

    @Before(value = {"test(String)"})
    public void before(Holder<String> param, @Proxy Object proxiedInstance) {
        logger.info("NormalService 获取到的注入的示例"+proxiedInstance);
        logger.info("NormalService test方法的参数:param:" + param.get());
        param.set("参数被修改了");
        logger.info("NormalService test方法的参数被修改为:param:" + param.get());
    }

    @After(value = {"test(String)"})
    public void after(@Returned Holder<String> result, @Proxy Object proxiedInstance) {
        logger.info("NormalService 获取到的注入的示例"+proxiedInstance);
        result.set("如果你看到这个，说明NormalService test方法的结果已经被修改了");
    }

}
