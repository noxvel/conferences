package com.nextvoyager.conferences.controller.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet request listener
 *
 * @author Stanislav Bozhevskyi
 */
@WebListener
public class ServletRequestListener implements jakarta.servlet.ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger(ServletRequestListener.class);

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
        HttpSession currentSession = servletRequest.getSession();

        currentSession.setAttribute("originRequestURL", servletRequest.getPathInfo());
        currentSession.setAttribute("originRequestQuery", servletRequest.getQueryString());

        LOGGER.info("Request initialized to address - " + servletRequest.getRequestURI() +
                ", from remote IP: " + servletRequest.getRemoteAddr());
    }
}
