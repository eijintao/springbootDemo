package demo;

import core.annotation.RequestMapping;
import core.common.ModelAndView;
import core.common.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * asus 梅锦涛
 * 2022/1/16
 *  处理器类：负责处理业务逻辑。
 * @author mjt
 */
public class HelloController {

    @RequestMapping("/hello.do")
    public String hello(HttpServletRequest request, ModelMap mm) {
        System.out.println("HelloController's hello()");
       // request.setAttribute("msg","helloworld,明天一定看完");
       // mm.addAttribute("msg", "hahhahhh");
        return "hello，这个是测试返回的是json";
    }

    @RequestMapping("/model.do")
    public ModelAndView model(ModelMap mm) {
        System.out.println("HelloController's model()");
        mm.addAttribute("msg", "先生之风，山高水长");
        return new ModelAndView("hello");
    }

}
