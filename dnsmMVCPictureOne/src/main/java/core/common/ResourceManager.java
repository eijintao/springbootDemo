package core.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/**
 * asus 梅锦涛
 * 2022/1/5
 * 资源管理类   单例模式
 * @author mjt
 */
public class ResourceManager {

    private static ResourceManager resourceManager;
    private TemplateEngine engine;

    // 拿到需要管理的资源
    public TemplateEngine getEngine() {
        return engine;
    }

//    public void setEngine(TemplateEngine engine) {
//        this.engine = engine;
//    }

    /**
     * 因为该构造器一定只会调用一次，所以可以将资源的创建过程相关代码放在这里，
     * 确保资源的创建只有一次
     * @param scrt
     */
    private ResourceManager (ServletContext scrt) {
        System.out.println("ResourceManager's Constructor()");
        // 模板解析器
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(scrt);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        // 防止页面重新加载的时候还用到缓存
        resolver.setCacheable(false);
        resolver.setCharacterEncoding("utf-8");

        // 模板引擎
        engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
    }

    public synchronized static ResourceManager getInstance(ServletContext scrt) {
        if (resourceManager == null) {
            resourceManager = new ResourceManager(scrt);
        }
        return resourceManager;
    }

}
