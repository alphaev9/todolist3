package com.alpha.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "F2_URLMapperFilter", urlPatterns = {"/", "/showList"})
public class F2_URLMapperFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("F2_URLMapperFilter");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();
        switch (path) {
            case "/":
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
                break;
            case "/showList":
                request.getRequestDispatcher("/WEB-INF/view/showList.jsp").forward(request, response);
                break;
        }
//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
