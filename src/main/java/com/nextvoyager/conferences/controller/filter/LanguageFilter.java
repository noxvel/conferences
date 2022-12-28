package com.nextvoyager.conferences.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LanguageFilter implements Filter {

    public static final String DEFAULT_LANG = "en";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (session != null && session.getAttribute("lang") == null) {
            session.setAttribute("lang", DEFAULT_LANG);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
