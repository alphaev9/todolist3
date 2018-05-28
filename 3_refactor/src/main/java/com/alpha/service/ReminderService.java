package com.alpha.service;

import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.BacklogState;
import com.alpha.repository.entity.User;
import com.alpha.webSocket.WsMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReminderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServletContext servletContext;

    /************
     *单位是毫秒，500秒
     * */
    @Scheduled(fixedRate =500000 )
    public void reminder() {
        Enumeration<String> attributes = servletContext.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String element = attributes.nextElement();
            Object attribute = servletContext.getAttribute(element);
            HttpSession session;
            if (attribute instanceof HttpSession) {
                session = (HttpSession) attribute;
                try {
                    reminder(session, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void reminder(HttpSession session, Integer advance) throws IOException, EncodeException {
        PersistId userId = (PersistId) session.getAttribute("userId");
        List<Backlog> allAdvent = getAllAdvent(userId, advance);
//        用webSocket向客户端推送消息
        WsMessageChannel reminder = (WsMessageChannel) session.getAttribute("WsMessageChannel");
        reminder.reminder(allAdvent);
    }

    private List<Backlog> getAllAdvent(PersistId userId, Integer advance) {
        User user = userRepository.getUserByPersistId(userId);
        List<Backlog> pendingList = user.getBacklogs().stream()
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
