package core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理器向视图对象传递数据。
 *
 * asus 梅锦涛
 * 2022/2/13
 *
 * @author mjt
 */
public class ModelMap {

    // data用于存放数据
    private Map<String, Object> data = new HashMap<>();

    /**
     * 绑定数据的方法
     * @param name
     * @param object
     */
    public void addAttribute(String name, Object object) {
        data.put(name, object);
    }

    public Map<String, Object> getData() {
        return data;
    }
}
