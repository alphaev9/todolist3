package com.alpha.controller;

import com.alpha.dao.BacklogMapper;
import com.alpha.dao.UserMapper;
import com.alpha.entity.Backlog;
import com.alpha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"display", "userId", "userName", "pendingList", "finishedList"})

public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BacklogMapper backlogMapper;
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(User user) {
        ModelAndView mv = new ModelAndView();
        List<User> users = userMapper.selectAll();
        boolean match = users.stream()
                .anyMatch(exsitingUser ->
                        exsitingUser.getName().equals(user.getName())
                );
        if (!match) {
            userMapper.insert(user);
//            sqlSession.commit();
            mv.addObject("display", "hide");
        } else {
            mv.addObject("display", "show");
        }
        mv.setView(new RedirectView("/"));
        return mv;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(User user) {
        ModelAndView mv = new ModelAndView();
        Integer userId = null;
        if (authentication(user)) {
            userId = userMapper.getIdByName(user.getName());
            mv.addObject("userId", userId);
            mv.addObject("userName", user.getName());
            mv.addObject("test","test");
        }
        List<Backlog> pendingList = new ArrayList<>();
        List<Backlog> finishedList = new ArrayList<>();

        List<Backlog> backlogList = backlogMapper.getAllByUserId(userId);

        backlogList.stream()
                .forEach(backlog -> {
                    switch (backlog.getState()) {
                        case pending:
                            pendingList.add(backlog);
                            break;
                        case finished:
                            finishedList.add(backlog);
                    }
                });

        mv.addObject("pendingList", pendingList);
        mv.addObject("finishedList", finishedList);
        RedirectView view = new RedirectView("/showList");

        mv.setView(view);
        view.setExposeModelAttributes(false);
        return mv;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, @ModelAttribute("userName") String userName, SessionStatus sessionStatus) {
        System.out.println("logout");
        servletContext.removeAttribute(userName);
        session.invalidate();
        sessionStatus.setComplete();

        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/");
        mv.setView(view);
        view.setExposeModelAttributes(false);

        return mv;
    }

    private boolean authentication(User user) {
        String password = userMapper.getPasswordByName(user.getName());
        if (password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
