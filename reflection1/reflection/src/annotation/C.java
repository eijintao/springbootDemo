package annotation;

/**
 * asus 梅锦涛
 * 2021/12/27
 *
 * @author mjt
 */
public class C {

    public void f1() {
        System.out.println("C's f1()");

    }

    @Test(value = "123")
    public void f2() {
        System.out.println("C's f2()");

    }
}
