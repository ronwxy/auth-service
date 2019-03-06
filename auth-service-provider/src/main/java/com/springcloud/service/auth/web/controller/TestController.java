package com.springcloud.service.auth.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.autoconfig.error.exception.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @HystrixCommand
    @GetMapping
    public String test() {
        throw new BizException("biz exception");
    }
}
