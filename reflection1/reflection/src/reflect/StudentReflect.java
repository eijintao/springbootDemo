package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * asus 梅锦涛
 * 2021/12/23
 *
 * @author mjt
 *
 * 对Student类进行解析
 */
public class StudentReflect {



    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Class<Student> stuClass = Student.class;

        /*// 解析这个类
        *//**
         * 获得到这个类的属性和名称和类型
         *//*
        Field[] fields = stuClass.getDeclaredFields();
        Stream.of(fields).map(field -> field.getName()).forEach(x -> System.out.println("student的类的属性名称："+x));
        Stream.of(fields).map(field -> field.getType()).forEach(x -> System.out.println("student的类的属性名称类型："+x));

        *//**
         * 获得到类的构造方法
         *//*
        Constructor<?>[] declaredConstructors = stuClass.getDeclaredConstructors();
        Stream.of(declaredConstructors).map(Constructor::getName).forEach(x -> System.out.println("当前构造方法名是："+x));
        // .forEach(j -> System.out.println("当前构造方法中的参数是："+j));
        for (Constructor<?> deConstuc : declaredConstructors) {
            Class<?>[] parameterTypes = deConstuc.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.println(parameterType);
                System.out.println("当前构造方法中的参数是："+parameterType.getName());
            }
        }

        *//**
         * 获得类的方法
         * .getMethods():获得到父类的能访问到的方法
         * .getDeclaredMethods():获得当前类声明的所有方法，但不包含父类方法
         *//*
        Method[] methods = stuClass.getMethods();
        Method[] declaredMethods = stuClass.getDeclaredMethods();
      //  Stream.of(methods).forEach(System.out::println);
        Stream.of(declaredMethods).forEach(x -> System.out.println("student的方法："+x));
        Stream.of(declaredMethods).forEach(x -> System.out.println("student的方法"+x+"，方法返回的类型是："+x.getReturnType()));
       */
        /* Stream.of(declaredMethods)
                .filter(x -> x.getName().equals("say"))
                .forEach(x -> {
            try {
                System.out.println("student的方法："+x.invoke(stuClass.newInstance()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });*/

        /**
         * 有参的调用的方式。
         */
        Class<?> aClass = Class.forName("reflect.Student");
        Object o = aClass.newInstance();
        Method say = aClass.getMethod("say",String.class);
        Object invoke = say.invoke(o,"hi");
        System.out.println(invoke);
    }

}
