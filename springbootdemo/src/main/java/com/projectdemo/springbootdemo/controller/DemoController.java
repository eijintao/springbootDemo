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
        return "你好，springBoot";
    }

}
