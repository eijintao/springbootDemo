package core.common;

import java.lang.reflect.Method;

/**
 * Handler封装了处理器实例及其方法对应的Method对象，可以方便利用java反射来调用其方法。
 *
 *
 * asus 梅锦涛
 * 2022/2/12
 *
 * @author mjt
 */
public class Handler {
    // object 是处理器实例
    private Object object;

    // method 是处理器方法对应的Method对象
    private Method method;

    public Handler(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
