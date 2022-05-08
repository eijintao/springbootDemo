package com.projectdemo.springbootdemo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
@Data
// @Configuration注解表示当前类是一个自定义配置类，并添加为Spring容器的组件，这里也可以使用传统的@Component注解；
@Configuration
// @PropertySource("classpath:test.properties")注解指定了自定义配置文件的位置和名称，此示例
//表示自定义配置文件为classpath类路径下的test.properties文件；
@PropertySource("classpath:test.properties")
// 如果配置类上使用的是@Component注解而非@Configuration注解，那么
//@EnableConfigurationProperties注解还可以省略
@EnableConfigurationProperties(MyProperties.class)
// @ConfigurationProperties(prefix = "test")注解将上述自定义配置文件test.properties中以test开
//头的属性值注入到该配置类属性中。
@ConfigurationProperties(prefix = "test")
public class MyProperties {

    private int id;
    private String name;

}
