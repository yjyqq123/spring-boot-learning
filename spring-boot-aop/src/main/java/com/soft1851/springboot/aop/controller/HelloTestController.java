package com.soft1851.springboot.aop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloTestController {
    @RequestMapping(value = "/hello")
    public String hello(String name){
        return "Hello " +name;
    }
}
