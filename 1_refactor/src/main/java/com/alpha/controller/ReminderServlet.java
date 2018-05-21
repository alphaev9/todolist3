package com.alpha.controller;


import com.alpha.dao.BacklogMapper;
import com.alpha.entity.Backlog;
import com.alpha.entity.BacklogState;
import com.alpha.webSocket.WsMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ReminderServlet", loadOnStartup = 1, value = "/timer",
        initParams = {
                @WebInitParam(name = "advance", value = "5"),
                @WebInitParam(name = "time", value = "16:08:00")})
public class ReminderServlet extends HttpServlet {
    @Autowired
    private BacklogMapper backlogMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Integer advance = Integer.valueOf(config.getInitParameter("advance"));
        String time = config.getInitParameter("time");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ServletContext servletContext = config.getServletContext();
                Enumeration<String> attributes = servletContext.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String element = attributes.nextElement();
                    Object attribute = servletContext.getAttribute(element);
                    HttpSession session = null;
                    if (attribute instanceof HttpSession) {
                        session = (HttpSession) attribute;
                        try {
                            reminder(session, advance);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (EncodeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        long daySpan = 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + time);
        String s = sdf.format(new Date());
        Date startTime = null;
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(task, startTime, daySpan);
        super.init(config);
    }

    private void reminder(HttpSession session, Integer advance) throws SQLException, ClassNotFoundException, IOException, EncodeException {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Backlog> allAdvent = getAllAdvent(userId, advance);
//        用webSocket向客户端推送消息
        WsMessageChannel reminder = (WsMessageChannel) session.getAttribute("WsMessageChannel");
        reminder.reminder(allAdvent);
    }

    private List<Backlog> getAllAdvent(Integer userId, Integer advance) throws SQLException, ClassNotFoundException {

        List<Backlog> pendingList = backlogMapper.getAllByUserId(userId).stream()
                .filter(backlog -> backlog.getState() == BacklogState.pending)
                .collect(Collectors.toList());


        List<Backlog> adventList = new ArrayList<>();
        for (Backlog backlog : pendingList) {
            java.sql.Date dueTime = backlog.getDueTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueTime);
            int due = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(new Date());
            int now = calendar.get(Calendar.DAY_OF_YEAR);

            if (due - now <= advance && due - now >= 0) {
                adventList.add(backlog);
            }
        }
        return adventList;
    }
}
