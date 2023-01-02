package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FrontController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Action action = ActionFactory.getAction(req);
            String view = action.execute(req, resp);
            if (view.equals(req.getPathInfo().substring(1))) {
                req.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(req, resp);
            } else {
                resp.sendRedirect(view);
            }
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}
