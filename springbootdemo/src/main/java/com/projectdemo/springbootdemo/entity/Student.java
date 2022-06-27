package com.projectdemo.springbootdemo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */

@Data
@Component
public class Student {

    /**
     * 测试id
     */
    @Value("${person.id}")
    private int id;

    /**
     * 测试学生名字
     */
    @Value("${person.name: 徐盛}")
    private String name;

}
