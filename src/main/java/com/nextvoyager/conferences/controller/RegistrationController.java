package com.nextvoyager.conferences.controller;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.user.UserDAO;
import com.nextvoyager.conferences.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req,resp);
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
        user.setRole(User.Role.USER);

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain UserDAO.
        UserDAO userDAO = javabase.getUserDAO();

        // Check if email exists.
        boolean exist = userDAO.existEmail(emailParam);

        if (exist) {
            req.setAttribute("message", "The email you entered already exists. Please enter a different email.");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else {
            userDAO.create(user);

            req.getSession().setAttribute("user", user);
            resp.sendRedirect("home");
        }
    }
}
