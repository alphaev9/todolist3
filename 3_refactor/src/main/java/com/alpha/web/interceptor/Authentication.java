package com.alpha.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Authentication extends HandlerInterceptorAdapter {
    @Autowired
    private HttpSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Authentication.........");
        String path = request.getServletPath();
        if (session.getAttribute("userId") == null) {
            if (path.equals("/") || path.equals("/login") || path.equals("/register")) {
                return true;
            } else {
                response.sendRedirect("/");
                return false;
            }
        } else {
            if (path.equals("/") || path.equals("/login")) {
                response.sendRedirect("/showList");
                return false;
            } else {
                return true;
            }
        }
    }
}
