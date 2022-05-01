package com.example.shuttledemo.controller;

import com.example.shuttledemo.service.NormalService;
import com.example.shuttledemo.service.SkipService;
import com.example.shuttledemo.service.SpringService;
import me.ezerror.shuttle.context.mvc.configuration.ShuttleRequestMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;

/**
 * 示例受代理方法
 *
 * @author sy
 * @date 2021/11/2
 **/
@RestController
@RequestMapping("/test")
public class DemoController {

    private static final Log logger = LogFactory.getLog(DemoController.class);

    @Autowired
    private SpringService springService;
    @Autowired
    ShuttleRequestMapping config;
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;
    /**
     * 受代理接口
     *
     * @param param
     * @return
     */
    @RequestMapping("/alterController")
    public String alterController(@RequestParam("param") String param) {
        System.out.println(config.shuttleMapping().getHandlerMethods());
        System.out.println(requestMappingHandlerMapping.getHandlerMethods());
        logger.info("alterController 参数:" + param);
        return "如果你看到这个，说明 alterController 这个接口的结果没有被修改";
    }

    @RequestMapping("/middleAlter")
    public String middleAlter(@RequestParam HashMap<String, String> param) {
        logger.info("middleAlter 参数:" + param);
        return "如果你看到这个，说明 middleAlter 这个接口的结果没有被修改";
    }


    @RequestMapping("/alterSpringService")
    public String springService() {
        return springService.test("springService 修改之前的参数");

    }

    @RequestMapping("/alterNormalService")
    public String normalService() {
        return new NormalService().test("NormalService 修改之前的参数");
    }

    @RequestMapping("/testSkipService")
    public String skipService() {
        return new SkipService().test("skipService 修改之前的参数");
    }


    @RequestMapping("/alterByRemoteController")
    public String test(@RequestParam(value = "param") String param) {
        logger.info("alterByRemoteController 参数:" + param);
        return "如果你看到这个，说明 alterByRemoteController 这个接口的结果并没有被远程方法修改";
    }
}
