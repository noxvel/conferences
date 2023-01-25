package com.nextvoyager.conferences.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

/**
 * Encoding filter for requests
 *
 * @author Stanislav Bozhevskyi
 */
@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name="encoding", value = "UTF-8"))
public class EncodingFilter implements Filter {

    String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
    }

}
