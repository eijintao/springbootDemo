package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * asus 梅锦涛
 * 2021/12/27
 *
 * @author mjt
 *
 *
 * 演示java注解的处理过程
 */
public class AnnotationDemo {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入类的路径：");
        String s = scanner.nextLine();
        Class<?> aClass = Class.forName(s);
        System.out.println("aClass的类名是："+ aClass.getName());
        /**
         * 一、newInstance()和new()区别：
         *
         * 　　1、两者创建对象的方式不同，前者是实用类的加载机制，后者则是直接创建一个类：
         *
         * 　　2、newInstance创建类是这个类必须已经加载过且已经连接，new创建类是则不需要这个类加载过
         *
         * 　　3、newInstance: 弱类型(GC是回收对象的限制条件很低，容易被回收)、低效率、只能调用无参构造，new 强类型（GC不会自动回收，只有所有的指向对象的引用被移除是才会被回收，若对象生命周期已经结束，但引用没有被移除，经常会出现内存溢出）
         */
        Object o = aClass.getDeclaredConstructor().newInstance();

        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            // 获得加在方法前的注解 即@Test
            Test annotation = declaredMethod.getAnnotation(Test.class);
            System.out.println("Test的注解："+annotation);
            if (annotation != null) {
                declaredMethod.invoke(o);
                // 读取注解的属性
                String value = annotation.value();
                int age = annotation.age();
                System.out.println("value的值："+ value + "age的值是：" +age);

            }
        }
    }


}
