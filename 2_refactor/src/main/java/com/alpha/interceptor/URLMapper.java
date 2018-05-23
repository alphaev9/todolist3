package com.alpha.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLMapper extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("URLMapper......");
        String path = request.getServletPath();
        switch (path) {
            case "/":
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
                break;
            case "/showList":
                request.getRequestDispatcher("/WEB-INF/view/showList.jsp").forward(request, response);
                break;
        }
        return false;
    }
}
