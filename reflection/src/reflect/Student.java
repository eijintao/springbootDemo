package reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2021/12/23
 *
 * @author mjt
 */
public class Student {

    private String id ;

    private String name;

    private int age;

    public Student() {
    }

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void show() {
        System.out.println("id："+ id);
        System.out.println("姓名："+ name);
        System.out.println("年龄："+ age);

    }

    @Override
    public String toString() {
        return "Strudent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    public String say(String msg) {
        this.name = "张三";
        Map<String, Object> sa = new HashMap<>();
        sa.put("1",2);
        List<Integer> list = new ArrayList<>();
        list.add(8);
       return name+"说："+msg;
    }
}
