package com.nextvoyager.conferences.controller.filter;

import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    FilterChain filterChain;
    @Mock
    HttpSession session;

    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @Test
    public void unauthorizedUser() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(null);
        when(req.getPathInfo()).thenReturn("/home");

        authorizationFilter.doFilter(req,resp,filterChain);
        verify(filterChain, times(1)).doFilter(req,resp);
    }

    @Test
    public void authorizedOrdinaryUser() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.ORDINARY_USER);
        when(req.getPathInfo()).thenReturn("/home");

        authorizationFilter.doFilter(req,resp,filterChain);
        verify(filterChain, times(1)).doFilter(req,resp);
    }

    @Test
    public void authorizedSpeaker() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.SPEAKER);
        when(req.getPathInfo()).thenReturn("/home");

        authorizationFilter.doFilter(req,resp,filterChain);
        verify(filterChain, times(1)).doFilter(req,resp);
    }

    @Test
    public void authorizedModerator() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.MODERATOR);
        when(req.getPathInfo()).thenReturn("/home");

        authorizationFilter.doFilter(req,resp,filterChain);
        verify(filterChain, times(1)).doFilter(req,resp);
    }

}
