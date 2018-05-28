package com.alpha.webSocket;


import com.alpha.repository.entity.Backlog;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@ServerEndpoint(value = "/wsChannel", configurator = GetHttpSessionConfigurator.class, encoders = MyEncoder.class)
public class WsMessageChannel {
    private Session session;

    @OnOpen
    public void wsOpen(Session session, EndpointConfig config) {
        System.out.println("ws opened: " + session.getId() + "  " + this);
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        httpSession.setAttribute("WsMessageChannel", this);
        this.session = session;
    }

    @OnClose
    public void wsClose(Session session) {
        System.out.println("ws closed: " + session.getId() + "  " + this);
    }

    public void kickOff() throws IOException, EncodeException {
        session.getBasicRemote().sendObject("you are kicked off");
    }

    public void reminder(List<Backlog> advents) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(advents);
    }

    @OnError
    public void onError(Throwable throwable) {
        String message = throwable.getMessage();
        System.out.println("error:  " + message);
    }
}
