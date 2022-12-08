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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req,resp);
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
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain UserDAO.
        UserDAO userDAO = javabase.getUserDAO();

        User user = userDAO.find(emailParam,passwordParam);

        if (user == null) {
            req.setAttribute("message", "Unknown username/password. Please retry");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("home");
        }
    }
}
