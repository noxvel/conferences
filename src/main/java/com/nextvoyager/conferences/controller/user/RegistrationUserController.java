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

import java.io.IOException;

@WebServlet("/user/registration")
public class RegistrationUserController extends HttpServlet {

    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/user/registration.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstNameParam = req.getParameter("firstName");
        String lastNameParam = req.getParameter("lastName");
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");

        // TODO: 01.12.2022 create validation to input data

        User user = new User();
        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
        user.setEmail(emailParam);
        user.setPassword(passwordParam);
        user.setRole(User.Role.ORDINARY_USER);

        boolean exist = userService.existEmail(emailParam);

        if (exist) {
            req.setAttribute("message", "The email you entered already exists. Please enter a different email.");
            req.getRequestDispatcher("/WEB-INF/jsp/user/registration.jsp").forward(req, resp);
        } else {
            userService.create(user);

            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
