package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pages/*")
public class FrontController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerAction controllerAction = ControllerActionFactory.getAction(req);
            String view = controllerAction.execute(req, resp);
            if (view.equals(req.getPathInfo().substring(1))) {
                req.getRequestDispatcher("/WEB-INF/jsp/" + view + ".jsp").forward(req, resp);
            } else {
//                resp.sendRedirect(req.getContextPath() + "/pages/" + view);
                resp.sendRedirect(view);
            }
        } catch (Exception e) {
//            throw new ServletException("Executing action failed.", e);
            e.printStackTrace();
        }
    }
}
