package com.alpha.web.interceptor;

import com.alpha.webSocket.WsMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;

public class KickOff extends HandlerInterceptorAdapter {
    @Autowired
    private ServletContext servletContext;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("KickOff......");
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        HttpSession sessionForAnotherDevice = (HttpSession) servletContext.getAttribute(name);

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
        return true;
    }
}
