package com.example.shuttledemo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 普通方法 - 测试不执行原始方法
 */
public class SkipService {
    private static final Log logger = LogFactory.getLog(SkipService.class);

    public String test(String param) {
        logger.info("SkipService：参数->" + param);
        return "SkipService:如果结果是这个，说明并没有跳过原始方法";
    }
}
