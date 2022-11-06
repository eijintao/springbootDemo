package reflect;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * asus 梅锦涛
 * 2021/12/23
 *
 * @author mjt
 *
 *
 * java反射机制的演示
 *
 * 获得一个类或对象的反射对象有三种常见方法
 *
 */
public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException {

        /**
         * 最常见的
         */
        Class<String> stringClass = String.class;

        /**
         * 类中就有了 类名，属性，方法，构造方法等
         *
         * str.getClass();
         */
        String str = "13";
        Class<? extends String> aClass = str.getClass();
        System.out.println("aClass：" + aClass);

        /**
         *  用包的地址来获取类
         */
        Class<?> aClass1 = Class.forName("java.lang.String");

        System.out.println("aClass1："+aClass1);

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入类名：");
        String className = scanner.nextLine();
        Class<?> cls = Class.forName(className);

        // 获得当前的类名
        String name = cls.getName();
        System.out.println("当前的类名："+name);

        // 获得当前类的公共方法
        Method[] methods = cls.getMethods();
        System.out.println("获得当前类的公共方法:");
        Stream.of(methods).map(Method::getName).forEach(System.out::println);

        // 获得当前类的所有方法（公共的，私有的）
        Method[] clsDeclaredMethods = cls.getDeclaredMethods();
        System.out.println("获得当前类的所有方法（公共的，私有的）:");
        Stream.of(clsDeclaredMethods).map(Method::getName).forEach(System.out::println);

    }

}
