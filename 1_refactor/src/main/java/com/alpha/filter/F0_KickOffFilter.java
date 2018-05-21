package com.alpha.filter;


import com.alpha.webSocket.WsMessageChannel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import java.io.IOException;

@WebFilter(filterName = "F0_KickOffFilter", urlPatterns = {"/login"})
public class F0_KickOffFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //检查是否已有设备登录
        System.out.println("KickOff");
        HttpServletRequest request = (HttpServletRequest) req;
        String name = request.getParameter("name");
        ServletContext servletContext = request.getServletContext();
        HttpSession sessionForAnotherDevice = (HttpSession) servletContext.getAttribute(name);
        HttpSession session = request.getSession();

        if (sessionForAnotherDevice == null) {
            servletContext.setAttribute(name, session);
        } else {
            //通过WebSocket发送下线消息
            WsMessageChannel kickOff = (WsMessageChannel) sessionForAnotherDevice.getAttribute("WsMessageChannel");
            try {
                kickOff.kickOff();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            sessionForAnotherDevice.invalidate();
            servletContext.removeAttribute(name);
            servletContext.setAttribute(name, session);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) {
    }

}
