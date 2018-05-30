package com.alpha.web.controller;

import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes({"display", "userId", "userName"})

public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(User user) {
        System.out.println("register..............");
        ModelAndView mv = new ModelAndView();
        List<User> users = userRepository.getAllUsers();
        boolean match = users.stream()
                .anyMatch(exsitingUser ->
                        exsitingUser.getName().equals(user.getName())
                );
        if (!match) {
            userRepository.addUser(user);
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
        PersistId userId;
        if (authentication(user)) {
            userId = userRepository.getUserByName(user.getName()).getId();
            mv.addObject("userId", userId);
            mv.addObject("userName", user.getName());
            mv.addObject("test", "test");
        }
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
        String password = userRepository.getUserByName(user.getName()).getPassword();
        if (password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
