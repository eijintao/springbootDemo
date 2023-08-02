package com.projectdemo.springbootdemo;



//import beans.SimpleBean;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.google.gson.Gson;
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
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import darabonba.core.client.ClientOverrideConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.util.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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


    @Value("${sms.secretid}")
    private String secretid;

    @Value("${sms.secretkey}")
    private String secretkey;

    @Value("${sms.region}")
    private String region;

    @Value("${sms.tencentcloud}")
    private String tencentcloud;

    @Value("${sms.phoneNumbers}")
    private String phoneNumbers;

    @Value("${sms.smssdkappid}")
    private String smssdkappid;

    @Value("${sms.templateid}")
    private String templateid;

    @Value("${sms.signname}")
    private String signname;



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
        String name = person.getName();
        List<String> hobby = person.getHobby();
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
        String name = person.getName();
        List<Person> person1 = new ArrayList<>();
        List<Person> collect = StreamUtils.fromNullable(person).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void notNUllTest() {
        Person person = new Person();
        String name = person.getName();
        System.out.println(name);
        //List<@NonNull Person> person1 = new ArrayList<>();

    }

    // 使用 AES加密
    @Test
    public void testAESKit () {
        String root123 = AESKit.encrypt("root123");
        System.out.println(root123);
    }

    @Test
    public void jsonTest () {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("username", "ydzq100");
        array.add(object);
        System.out.println(object);
        System.out.println(array.toString());


        Map<String, Object> mapParams = new HashMap<>();
        List<Map<String, Object>>  listParams = new ArrayList<>();
        mapParams.put("username",123);
        mapParams.put("realName",312);
        mapParams.put("password",312);
        listParams.add(mapParams);
        String s = JSONObject.toJSONString(listParams);
        System.out.println(listParams.toString());
        System.out.println(s);
    }

    /**
     * 通过腾讯云短信，发送短信
     */
    @Test
    public void testStream() {
        // 实例化一个认证对象，入参需要传入腾讯云账户 secretId 和 secretKey
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(secretid, secretkey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(tencentcloud);
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, region, clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 =  {"18040583457"};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId(smssdkappid);
            req.setSignName(signname);
            req.setTemplateId(templateid);

            String[] templateParamSet1 =  {"sys", "公共管理审批", "数据资产管理平台"};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }


}
