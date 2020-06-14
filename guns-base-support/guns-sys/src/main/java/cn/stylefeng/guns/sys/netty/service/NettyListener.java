package cn.stylefeng.guns.sys.netty.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NettyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.err.println("EchoServerListener Startup!");
        new Thread(){
            @Override
            public	void run(){
                try {
                    new EchoServer(9000,9999).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.err.println("EchoServerListener end!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
