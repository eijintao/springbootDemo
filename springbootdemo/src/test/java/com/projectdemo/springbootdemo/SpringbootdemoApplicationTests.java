package com.projectdemo.springbootdemo;



//import beans.SimpleBean;
import com.projectdemo.springbootdemo.Repository.CommentRepository;
import com.projectdemo.springbootdemo.controller.DemoController;
import com.projectdemo.springbootdemo.entity.Article;
import com.projectdemo.springbootdemo.entity.Comment;
import com.projectdemo.springbootdemo.entity.MyProperties;
import com.projectdemo.springbootdemo.entity.Person;
import com.projectdemo.springbootdemo.entity.RepositoryComment;
import com.projectdemo.springbootdemo.entity.Student;
import com.projectdemo.springbootdemo.mapper.ArticleMapper;
import com.projectdemo.springbootdemo.mapper.CommentMapper;
import com.projectdemo.springbootdemo.utils.AESKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
// 这个是 自己自定义的jar包在这里的测试的。
//    @Autowired
//    private SimpleBean simpleBean;

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired
    private CommentRepository repository;


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
    //  @Value("${person.name: 徐盛}") ，如果 application.yaml等配置文件中没有相应的属性，那么
    //  Value注解，就会直接拿 徐盛  这两个字来进行输入和输出
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
//        System.out.println(simpleBean);
    }

    // 自动创建的单元测试方法实例 测试 注解mybatis  比如 @select
    @Test
    public void test6() {
        Comment comment = commentMapper.findById(1);
        System.out.println(comment);
    }

    // 自动创建的单元测试方法实例 测试 mybatis的xml形式  比如 ArticleMapper.xml
    @Test
    public void test7() {
        Article article = articleMapper.selectArticle(1);
        System.out.println(article);
    }

    // 自动创建的单元测试方法实例 测试 jpa  比如 CommentRepository extends JpaRepository<RepositoryComment,Integer>
    @Test
    public void test8() {
        Optional<RepositoryComment> optional = repository.findById(1);
        if(optional.isPresent()){
            System.out.println(optional.get());
        }
        System.out.println();
    }

    @Test
    public void testSteamUtils() {
        Person person = new Person();
        List<Person> collect = StreamUtils.fromNullable(person).collect(Collectors.toList());
        System.out.println(collect);

    }

    // 使用 AES加密
    @Test
    public void testAESKit () {
        String root123 = AESKit.encrypt("root123");
        System.out.println(root123);
    }

}
