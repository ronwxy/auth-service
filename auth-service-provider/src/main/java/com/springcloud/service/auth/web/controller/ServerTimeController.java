package com.springcloud.service.auth.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("time")
public class ServerTimeController {
    @GetMapping("server")
    public Long serverTime() {
        return new Date().getTime();
    }
}
