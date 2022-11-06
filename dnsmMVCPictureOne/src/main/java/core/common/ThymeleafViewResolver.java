package core.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这是个视图解析器类，负责解析视图名。
 *
 * asus 梅锦涛
 * 2022/2/13
 *
 * @author mjt
 */
public class ThymeleafViewResolver {



    public void processViewName(String viewName,ModelMap mm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         *  使用thymeleaf来处理视图名 ,如果视图名是以“redirect：”开头，则重定向，否则转发。
         */
        if (viewName.startsWith("redirect:")) {
            // 之所以重定向，是因为 在不重定向的情况下，在登录的当前页面中，地址栏中的url还是登录的url，刷新页面的时候
            // 很容易造成二次登录，不仅造成资源的浪费，也是一个很重大的问题。
            // 构建重定向地址
            String redirectPath = request.getContextPath()+"/"+viewName.substring("redirect:".length());
            // 重定向
            response.sendRedirect(redirectPath );

        } else {
            TemplateEngine engine = ResourceManager.getInstance(request.getServletContext()).getEngine();
            WebContext context = new WebContext(request, response, request.getServletContext());
            // 将modelmap中存放的数据添加到webcontext
//                for (Map.Entry<String, Object> entry : mm.getData().entrySet()) {
//                    String key = entry.getKey();
//                    Object value = entry.getValue();
//                    context.setVariable(key,value);
//                }
            // 这一行代码可以代替上面的for循环代码。
            context.setVariables(mm.getData());
            response.setContentType("text/html;charset=utf-8");
            // 视图名，上下文，响应流
            engine.process(viewName,context,response.getWriter());
        }
    }
}
