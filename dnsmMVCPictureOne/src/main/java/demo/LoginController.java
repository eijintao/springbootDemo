package demo;

import core.annotation.RequestMapping;
import core.common.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * asus 梅锦涛
 * 2022/2/13
 *
 * @author mjt
 */
@RequestMapping("/demo")
public class LoginController {

    @RequestMapping("/toLogin.do")
    public ModelAndView toLogin() {
        System.out.println("LoginController's toLogin()");
        return new ModelAndView("login");
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request) {
        System.out.println("LoginController's login()");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:" + username + "和password:" + password);
        if ("tom".equals(username) && "123".equals(password)) {
            // 登录成功，重定向到欢迎页面
            return new ModelAndView("redirect:demo/gretting.do");
        }
        request.setAttribute("login_failed","用户名或密码错误");
        return new ModelAndView("login");
    }

    @RequestMapping("/gretting.do")
    public ModelAndView gretting() {
        System.out.println("LoginController's gretting()");
//        return "gretting";
        return new ModelAndView("gretting");
    }



}
