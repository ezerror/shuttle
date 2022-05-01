package com.example.alter.addcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新增一个接口
 *
 * @author sy
 * @date 2021/11/2
 **/
@RestController
@RequestMapping("new")
public class NewController {


    @RequestMapping("/test")
    public String test() {
        return "这是一个新增的接口";
    }
}
