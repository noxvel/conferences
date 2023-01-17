package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/pages/*")
public class FrontController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(FrontController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerAction controllerAction = ControllerActionFactory.getAction(req);
            String view = controllerAction.execute(req, resp);
            if (view.equals(req.getPathInfo())) {
                req.getRequestDispatcher("/WEB-INF/jsp" + view + ".jsp").forward(req, resp);
            } else {
                resp.sendRedirect(view);
            }
        } catch (Exception e) {
            LOG.error("Executing failed: Exception - " + e.getClass().getName() + ", message: "+ e.getMessage());
            throw new ServletException("Executing action failed.", e);
        }
    }
}
