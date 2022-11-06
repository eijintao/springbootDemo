package core.common;

import core.annotation.RequestMapping;
import sun.security.krb5.internal.PAData;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *  映射处理器类：
 *          负责建立请求路径与处理器实例及方法的对应关系。
 *          比如；
 *              请求路径为“ /hello.do”，则该请求为HelloController的hello方法来处理
 *
 * asus 梅锦涛
 * 2022/2/12
 *
 * @author mjt
 */
public class HandleMapping {

    // map用于存放请求路径与处理器实例及方法的对应关系
    private Map<String,Handler> map = new HashMap<>();

    /**
     * 负责建立请求路径与处理器实例及方法的对应关系。
     * @param beans 处理器实例组成的集合。
     */
    public void process(List beans) {
        System.out.println("HandleMapping's process()");
        // 对处理器实例进行遍历
        for (Object obj : beans) {
            // 获得加在处理器类前面的@RquestMapping注解
            RequestMapping annotation1 = obj.getClass().getAnnotation(RequestMapping.class);
            String pathTypeValue = "";
            if (annotation1 != null) {
                pathTypeValue = annotation1.value();
            }
            // 获得处理器中的所有方法
            Method[] declaredMethods = obj.getClass().getDeclaredMethods();
            for (Method mh : declaredMethods) {
                // 获得加载在方法前的注解拿到，即@RequestMapping
                RequestMapping annotation = mh.getAnnotation(RequestMapping.class);
                if (annotation != null) {
                    // 获得请求路径
                    String pathValue = annotation.value();
                    System.out.println("请求路径是pathValue:" + pathValue);
                    // 将请求路径与处理器实例及方法的对应关系保存下来
                    map.put(pathTypeValue + pathValue,new Handler(obj,mh));
                }

            }
        }
        System.out.println("请求路径与处理器实例及方法的对应关系map:" + map);
    }

    /**
     * 依据请求路径返回对应的Handler对象.
     * @param servletPath
     * @return
     */
    public Handler getHandler(String servletPath) {
        return map.get(servletPath);
    }
}
