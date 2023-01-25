package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * HttpServlet class that represents the Front controller pattern.
 * It takes all requests that go to paths with the prefix "/pages".
 * When appropriate action was found, it compares the result of
 * execution with the request path, and if it equals, then it forwards to the JSP page.
 * In another case, it sends a redirect to this path.
 *
 * @author Stanislav Bozhevskyi
 */
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
                resp.sendRedirect(req.getContextPath() + view);
            }
        } catch (Exception e) {
            LOG.error("Executing failed: Exception - " + e.getClass().getName() + ", message: "+ e.getMessage());
            throw new ServletException("Executing action failed.", e);
        }
    }
}
