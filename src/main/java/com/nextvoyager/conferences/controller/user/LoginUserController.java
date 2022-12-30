package com.nextvoyager.conferences.controller.user;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/login")
public class LoginUserController extends HttpServlet {

    private final UserService userService = AppContext.getInstance().getUserService();
    private static final Logger logger = LogManager.getLogger(LoginUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");

//        // Prepare messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        request.setAttribute("messages", messages);
//
//        // Get and validate name.
//        String name = request.getParameter("name");
//        if (name == null || name.trim().isEmpty()) {
//            messages.put("name", "Please enter name");
//        } else if (!name.matches("\\p{Alnum}+")) {
//            messages.put("name", "Please enter alphanumeric characters only");
//        }
//
//        // Get and validate age.
//        String age = request.getParameter("age");
//        if (age == null || age.trim().isEmpty()) {
//            messages.put("age", "Please enter age");
//        } else if (!age.matches("\\d+")) {
//            messages.put("age", "Please enter digits only");
//        }
//
//        // No validation errors? Do the business job!
//        if (!messages.isEmpty()) {
//            messages.put("success", String.format("Hello, your name is %s and your age is %s!", name, age));
//        }

        // Obtain DAOFactory.
        User user = userService.find(emailParam,passwordParam);

        if (user == null) {
            req.setAttribute("message", "Unknown username/password. Please retry");
            req.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(req, resp);

            logger.warn("Bad login for user with email: " + emailParam);
        } else {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            logger.info("user is login - " + user.getEmail());
            resp.sendRedirect("home");
        }
    }
}
