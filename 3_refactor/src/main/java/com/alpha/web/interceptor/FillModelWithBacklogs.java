package com.alpha.web.interceptor;

import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class FillModelWithBacklogs extends HandlerInterceptorAdapter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("FillModelWithBacklogs...........");
        PersistId userId = (PersistId) session.getAttribute("userId");
        User user = userRepository.getUserByPersistId(userId);
        List<Backlog> backlogs = user.getBacklogs();
        List<Backlog> pendingList = new ArrayList<>();
        List<Backlog> finishedList = new ArrayList<>();
        backlogs.stream()
                .forEach(backlog -> {
                    System.out.println(backlog.getCooperators().isEmpty());
                    switch (backlog.getState()) {
                        case pending:
                            pendingList.add(backlog);
                            break;
                        case finished:
                            finishedList.add(backlog);
                    }
                });
        session.setAttribute("pendingList", pendingList);
        session.setAttribute("finishedList", finishedList);

        RedirectView view = new RedirectView("/showList");

        modelAndView.setView(view);
        view.setExposeModelAttributes(false);
    }
}
