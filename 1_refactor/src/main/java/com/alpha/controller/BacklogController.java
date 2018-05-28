package com.alpha.controller;

import com.alpha.dao.BacklogMapper;
import com.alpha.entity.Backlog;
import com.alpha.entity.BacklogState;
import com.alpha.entity.Cooperator;
import com.alpha.service.EmailTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Controller
@SessionAttributes({"pendingList", "finishedList", "userId"})
public class BacklogController {
    @Autowired
    private BacklogMapper backlogMapper;
    @Autowired
    private ServletContext servletContext;
    @RequestMapping(value = "memo", method = RequestMethod.POST)
    public ModelAndView memo(Backlog backlog, @RequestParam("attachmentFile") MultipartFile file, @ModelAttribute("userId") Integer userId) {
        /*处理文件上传*/
        if (!file.isEmpty()) {
            String root = servletContext.getRealPath("/");
            String saveLocation = root + "\\" + backlog.getAttachment();
            File fileSaved = new File(saveLocation);
            if (!fileSaved.isDirectory()) {
                fileSaved.mkdir();
            }
            try {
                file.transferTo(new File(saveLocation + "\\" + file.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            backlog.setAttachment(backlog.getAttachment() + "\\" + file.getOriginalFilename());
        }
        backlog.setState(BacklogState.pending);
        backlog.setUserid(userId);
        backlogMapper.insert(backlog);

        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/showList");
        mv.setView(view);

        view.setExposeModelAttributes(false);
        fillModel(mv, userId);
        return mv;
    }

    @RequestMapping(value = {"/removePending", "/removeFinished"})
    public ModelAndView remove(@RequestParam("backlogId") Integer[] ids,@ModelAttribute("userId") Integer userId) {
        for (Integer id : ids) {
            Backlog backlog = backlogMapper.selectByPrimaryKey(id);
            String attachment = backlog.getAttachment();
            if (attachment != null) {
                String root = servletContext.getRealPath("/");
                new File(root + "/" + attachment).delete();
            }
            backlogMapper.deleteByPrimaryKey(id);
        }
        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/showList");
        mv.setView(view);
        view.setExposeModelAttributes(false);

        fillModel(mv, userId);
        return mv;
    }

    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public ModelAndView finish(@RequestParam("backlogId") Integer[] ids,@ModelAttribute("userId") Integer userId) {
        for (Integer id : ids) {
            Backlog backlog = backlogMapper.selectByPrimaryKey(id);
            backlog.setState(BacklogState.finished);
            backlogMapper.updateByPrimaryKey(backlog);
            List<Cooperator> cooperators = backlog.getCooperators();
            cooperators.forEach(cooperator -> {
                    EmailTask emailTask = new EmailTask(cooperator, "smtp.163.com");
                    emailTask.setSubject("backlog is finished!");
                    emailTask.setText("sir,the backlog is finished!");
                    new Thread(emailTask).start();

            });
        }

        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/showList");
        mv.setView(view);
        view.setExposeModelAttributes(false);

        fillModel(mv, userId);
        return mv;
    }

    @RequestMapping(value = "redo", method = RequestMethod.POST)
    public ModelAndView redo(@RequestParam("backlogId") Integer[] ids,@ModelAttribute("userId") Integer userId) {
        for (Integer id : ids) {
            Backlog backlog = backlogMapper.selectByPrimaryKey(id);
            backlog.setState(BacklogState.pending);
            backlogMapper.updateByPrimaryKey(backlog);
            List<Cooperator> cooperators = backlog.getCooperators();
            cooperators.forEach(cooperator -> {
                EmailTask emailTask = new EmailTask(cooperator, "smtp.163.com");
                emailTask.setSubject("backlog will redo!");
                emailTask.setText("sir,the backlog will redo!");
                new Thread(emailTask).start();
            });
        }
        ModelAndView mv = new ModelAndView();
        RedirectView view = new RedirectView("/showList");
        mv.setView(view);
        view.setExposeModelAttributes(false);

        fillModel(mv, userId);
        return mv;
    }

    private void fillModel(ModelAndView mv, Integer userId) {
        List<Backlog> backlogs = backlogMapper.getAllByUserId(userId);
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
        mv.addObject("pendingList", pendingList);
        mv.addObject("finishedList", finishedList);
    }
}
