package nl.dvberkel;

import nl.dvberkel.context.TreeContextConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(createServletContextHandler(getContext()));
        server.start();
        server.join();
    }

    private static Handler createServletContextHandler(WebApplicationContext context) {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setErrorHandler(null);
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        handler.addEventListener(new ContextLoaderListener(context));
        return handler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(TreeContextConfiguration.class);
        return context;
    }
}
