package com.projectdemo.springbootdemo;


import com.projectdemo.springbootdemo.controller.DemoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // 测试启动器，并加载Spring Boot测试注解
@SpringBootTest // 标记为Spring Boot单元测试类，并加载项目的ApplicationContext上下文环境
public class SpringbootdemoApplicationTests {

    @Autowired
    private DemoController demoController;

    /**
     * todo:
     *   1: 问题1： RunWith该注解爆红，是因为在当前2.6.6版本中，启动依赖test没有指定版本
     *   2：问题2： Test注解没有绿色标志启动，是因为 当前测试类和方法上没有添加public
     *
     */

    // 自动创建的单元测试方法实例
    @Test
    public void test1() {
        String demo = demoController.demo();
        System.out.println(demo);
    }


}
