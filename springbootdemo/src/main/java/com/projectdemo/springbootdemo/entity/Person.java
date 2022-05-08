package com.projectdemo.springbootdemo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/5/7
 *
 * @author mjt
 */

@Component //用于将Person类作为Bean注入到Spring容器中
@ConfigurationProperties(prefix = "person")  //将配置文件中以person开头的属性注入到该类中
@Data
public class Person {

    /***
     * 测试的id
     */
    private int id;

    /**
     * 测试的名字
     */
    private String name;

    /**
     *
     */
    private List hobby;

    /**
     * 测试的家庭成员。数组的形式
     */
    private String[] family;

    /**
     * 测试的map的结构数据
     */
    private Map map;

    /**
     * 测试的pet类型的数据，其中有type，name属性
     */
    private Pet pet;


}
