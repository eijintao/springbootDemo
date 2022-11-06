package core.common;

/**
 *
 * 处理器返回给控制器的一个对象，用于存放视图名和数据。
 * asus 梅锦涛
 * 2022/2/13
 *
 * @author mjt
 */
public class ModelAndView {

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
