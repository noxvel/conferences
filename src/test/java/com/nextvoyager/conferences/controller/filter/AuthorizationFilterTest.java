package com.nextvoyager.conferences.controller.filter;

import com.nextvoyager.conferences.model.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
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

    private static List<String> allUserAccess;
    private static List<String> authorizedUserAccess;
    private static List<String> ordinaryUserAccess;
    private static List<String> speakerAccess;
    private static List<String> moderatorAccess;

    @BeforeAll
    public static void setUp(){
        allUserAccess = AuthorizationFilter.getAllUserAccess();
        authorizedUserAccess = AuthorizationFilter.getAuthorizedUserAccess();
        ordinaryUserAccess = AuthorizationFilter.getOrdinaryUserAccess();
        speakerAccess = AuthorizationFilter.getSpeakerAccess();
        moderatorAccess = AuthorizationFilter.getModeratorAccess();
    }

    @Test
    public void unauthorizedUser() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(null);

        for (String path: allUserAccess) {
            when(req.getPathInfo()).thenReturn(path);
            authorizationFilter.doFilter(req,resp,filterChain);
        }

        verify(filterChain, times(allUserAccess.size()).description("Number of available paths for unauthorized user"))
                .doFilter(req,resp);
    }

    @Test
    public void unauthorizedUserDenyAccess() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(null);

        for (String path: authorizedUserAccess) {
            when(req.getPathInfo()).thenReturn(path);
            authorizationFilter.doFilter(req,resp,filterChain);
        }

        verify(filterChain, times(0).description("Number of unavailable paths for unauthorized user"))
                .doFilter(req,resp);
    }

    @Test
    public void authorizedOrdinaryUser() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.ORDINARY_USER);

        for (String path: ordinaryUserAccess) {
            when(req.getPathInfo()).thenReturn(path);
            authorizationFilter.doFilter(req,resp,filterChain);
        }

        verify(filterChain, times(ordinaryUserAccess.size()).description("Number of available paths for ordinary user"))
                .doFilter(req,resp);
    }

    @Test
    public void authorizedSpeaker() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.SPEAKER);

        for (String path: speakerAccess) {
            when(req.getPathInfo()).thenReturn(path);
            authorizationFilter.doFilter(req,resp,filterChain);
        }

        verify(filterChain, times(speakerAccess.size()).description("Number of available paths for speaker"))
                .doFilter(req,resp);
    }

    @Test
    public void authorizedModerator() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userRole")).thenReturn(User.Role.MODERATOR);

        for (String path: moderatorAccess) {
            when(req.getPathInfo()).thenReturn(path);
            authorizationFilter.doFilter(req,resp,filterChain);
        }

        verify(filterChain, times(moderatorAccess.size()).description("Number of available paths for moderator"))
                .doFilter(req,resp);
    }

}
