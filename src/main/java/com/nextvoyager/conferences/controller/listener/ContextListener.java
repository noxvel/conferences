package com.nextvoyager.conferences.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // initialize controller actions
        try {
            Class.forName("com.nextvoyager.conferences.controller.frontcontroller.ControllerActionFactory");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
