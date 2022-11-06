package core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.common.*;
import demo.HelloService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.management.remote.rmi._RMIConnection_Stub;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/1/5
 *  之所以加loadOnStartup是因为希望容器一启动完 ，servlet构造器就实例化。
 *  loadOnStartup:容器一启动完，servlet构造器就实例化。=1是如果有有几个servlet，数字越小，优先级越高。
 * @author mjt
 */
// @WebServlet(name = "DispatcherServlet",urlPatterns = "*.do",loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    // handleMapping是一个成员变量，不仅要在init()方法里面用到，也要在service()方法里面
    private HandleMapping handleMapping;

    private ThymeleafViewResolver viewResolver;

    public DispatcherServlet() {
        System.out.println("DispatcherServlet's Constructor()");
    }

    /**
     * 容器启动，只是初始化一次
     * 这个init()方法是 GenericServlet 的方法，此处重写了。并且重写的还是不带参数的init()方法
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        System.out.println("DispatcherServlet's init()");
        SAXReader saxReader = new SAXReader();
        // 通过初始化参数获得配置文件名 web.xml中的<param-name>configLocation</param-name>
        String configLocation = getInitParameter("configLocation");
        // todo 类加载器有个特点：可以根据classpath找资源. getClass()：获得类对象（即方法区当中DispatcherServlet类对象）
        // todo getClassLoader() 获得类加载器。(这里获得的是容器自带的类加载器，该类加载器会从classes下查找文件。
        //  注：当前dnsmmvc.xml会在classes文件夹下面)
        // todo 构建一个指向配置文件的输入流.
        InputStream in = getClass().getClassLoader().getResourceAsStream(configLocation);
        try {
            // 使用解析器解析配置文件
            Document doc = saxReader.read(in);
            // 找到根节点
            Element rootElement = doc.getRootElement();
            // 获得根节点下面的所有子节点
            List<Element> elements = rootElement.elements();
            // beans集合用于存放处理器实例
            List beans = new ArrayList();
            for (Element element : elements) {
                //  获得处理器类名
                String aClass = element.attributeValue("class");
                System.out.println("处理器类名aClass:" + aClass );
                // 将处理器实例化
                Object obj = Class.forName(aClass).newInstance();
                // 将处理器实例放到集合里，方便管理
                beans.add(obj);
            }
            System.out.println("beans的list集合：" + beans);
            // 创建映射处理器
            handleMapping = new HandleMapping();
            // 调用映射处理器的方法来处理处理器实例
            handleMapping.process(beans);
            // 创建视图解析器。
            viewResolver = new ThymeleafViewResolver();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DispatcherServlet类中的service方法进来了");
        String servletPath = request.getServletPath();
        System.out.println("这个路径的名字是：" + servletPath);

        // 这个表示前端输入框输入的是中文时候，确保到后台不是乱码。
        request.setCharacterEncoding("utf-8");

        // 调用HandlerMapping的方法来获得与该请求对应的Handler对象。
        Handler handler = handleMapping.getHandler(servletPath);
        System.out.println("返回的handler：" + handler);
        // 如果与该请求对应的Handler对象不存在（即没有对应的处理器），则给开发人员相应提示
        if (handler == null) {
            System.out.println("请求路径为：" + servletPath + "没有对应的处理器");
            response.sendError(404);
            return;
        }
        // 利用handler对象来调用处理器方法
        Object object = handler.getObject();
        System.out.println("handler.getObject()的结果：" + object);
        Method method = handler.getMethod();
        System.out.println("handler.getMethod()的结果：" + method);
        // viewName视图名
        String viewName = null;
        // modelmap用于处理器向视图传递数据
        ModelMap mm = new ModelMap();
        try {
            // 调用处理器方法，首先获得处理器方法的参数类型信息
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object invoke = null;
            if (parameterTypes.length == 0 ) {
                // 调用不带参
                invoke = method.invoke(object);
            } else {
                // 调用带有参数的，obs里面用于存放实际参数值
                Object[] obs =  new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    // 参数是什么类型就把参数放到里面去
                    // 问题：如果是String类型，那么obs[i]的赋值又是从哪里赋值呢？
                    // 解答：提高其在service方法里的作用域，初始化一个初始变量即可。
                    if (parameterTypes[i] == HttpServletRequest.class) {
                        obs[i] = request;
                    }
                    if (parameterTypes[i] == HttpServletResponse.class) {
                        obs[i] = response;
                    }
                    if (parameterTypes[i] == ModelMap.class) {
                        obs[i] = mm;
                    }
                }
                // 调用处理器的方法（带参）
                invoke = method.invoke(object, obs);

            }
            // 如果处理器方法返回的是ModelAndView,则当作视图名来处理
            if (invoke instanceof ModelAndView) {
                // 获得视图名
                viewName = ((ModelAndView)invoke).getViewName();
                System.out.println("viewNname:" + viewName);

                // 调用视图解析器来解析视图名
                viewResolver.processViewName(viewName,mm,request,response);
            } else {
                // 否则，将处理器返回的结果转换成json
                response.setContentType("application/json;charset=utf-8");
                // 使用jackson api 将处理器返回的结果转换成json字符串
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(invoke);
                System.out.println("json:" + json);
                // 将json字符串发送给客户端
                response.getWriter().print(json);
            }

//            // 获得视图名
//            viewName = invoke.toString();

        }  catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

    }


}
