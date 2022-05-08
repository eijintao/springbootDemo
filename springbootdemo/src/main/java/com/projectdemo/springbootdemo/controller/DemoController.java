package com.projectdemo.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * asus 梅锦涛
 * 2022/5/7
 *
 * @author mjt
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo () {
        return "你好，springBoot,大是大非所发生的私房蛋糕";
    }

    @RequestMapping("/hello")
    public String hello () {
        return "你好，springBoot,这次是热部署少时诵诗书a,收费的规范化示范户";
    }
}
