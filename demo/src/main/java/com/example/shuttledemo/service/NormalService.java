package com.example.shuttledemo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 普通方法 - 新生成示例不是通过spring容器调用的方法
 */
public class NormalService {
    private static final Log logger = LogFactory.getLog(NormalService.class);

    public String test(String param) {
        logger.info("NormalService：参数->" + param);
        return "NormalService:如果结果是这个，说明结果并没有修改";
    }
}
