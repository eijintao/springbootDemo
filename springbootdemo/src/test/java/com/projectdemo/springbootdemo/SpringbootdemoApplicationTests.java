package com.projectdemo.springbootdemo;



import beans.SimpleBean;
import com.projectdemo.springbootdemo.controller.DemoController;
import com.projectdemo.springbootdemo.entity.MyProperties;
import com.projectdemo.springbootdemo.entity.Person;
import com.projectdemo.springbootdemo.entity.Student;
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

    @Autowired
    private Person person;

    @Autowired
    private Student student;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private SimpleBean simpleBean;

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

    // 自动创建的单元测试方法实例 测试person 也就是@ConfigurationProperties
    @Test
    public void test2() {
       // String demo = demoController.demo();
        System.out.println(person);
    }

    // 自动创建的单元测试方法实例 测试student，也就是@Value
    @Test
    public void test3() {
        // String demo = demoController.demo();
        System.out.println(student);
    }

    // 自动创建的单元测试方法实例 测试myproperties，也就是自定义配置文件,居然没有乱码。这个是怎么回事？
    @Test
    public void test4() {
        // String demo = demoController.demo();
        System.out.println(myProperties);
    }

    // 自动创建的单元测试方法实例 测试自定义start ，这个写入自定义starter也没有乱码了
    @Test
    public void test5() {
        // String demo = demoController.demo();
        System.out.println(simpleBean);
    }
}
