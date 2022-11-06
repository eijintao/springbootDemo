package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * asus 梅锦涛
 * 2021/12/27
 *
 * @author mjt
 *
 * 注解缺省只会保留到字节码文件里面，在类加载时会被删除（也就是说在运行期间就不存在了）
 * 可以通过@Retention元注解来指定注解的生存时间。
 * 注：
 *      元注解指的是由系统提供（jdk自带），并且用来解释其他注解的注解。
 *      注解的生存时间只有三个：
 *          只保留到源代码里面，编译之后会被删除。
 *          只保留到字节码文件里面，类加载时被删除。
 *          保留到运行期间。
 *
 * @Target 元注解用于指定注解的使用范围
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

    /**
     * 注解的类型可以是String，八种基本类型，枚举类型，Class类型，注解类型，以及由这些类型构成的数组。
     *
     * 一个注解只有一个属性，并且名为value的注解，则在使用该注解时，可以不用写属性名
     * @return
     */
    public String value();

    /**
     * 有了缺省值可以不赋值
     * @return
     */
    public int age() default 55;

}
