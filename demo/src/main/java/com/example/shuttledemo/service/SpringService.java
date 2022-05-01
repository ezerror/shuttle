package com.example.shuttledemo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringService {

    private static final Log logger = LogFactory.getLog(NormalService.class);

    public String test(String param) {
        logger.info("SpringService：参数->" + param);
        return "SpringService:如果结果是这个，说明结果并没有修改";
    }
}
