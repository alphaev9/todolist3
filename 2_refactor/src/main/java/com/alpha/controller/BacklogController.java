package com.alpha.controller;

import com.alpha.dao.BacklogMapper;
import com.alpha.entity.Backlog;
import com.alpha.entity.BacklogState;
import com.alpha.entity.Cooperator;
import com.alpha.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"userId"})
public class BacklogController {
    @Autowired
    private BacklogMapper backlogMapper;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private EmailService emailService;

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
        return mv;
    }

    @RequestMapping(value = {"/removePending", "/removeFinished"})
    public ModelAndView remove(@RequestParam("backlogId") Integer[] ids) {
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
        return mv;
    }


    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public ModelAndView finish(@RequestParam("backlogId") Integer[] ids) {
        for (Integer id : ids) {
            Backlog backlog = backlogMapper.selectByPrimaryKey(id);
            backlog.setState(BacklogState.finished);
            backlogMapper.updateByPrimaryKey(backlog);
            sendEmail(backlog, "backlog is finished!", "sir,the backlog is finished!");
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "redo", method = RequestMethod.POST)
    public ModelAndView redo(@RequestParam("backlogId") Integer[] ids) {
        for (Integer id : ids) {
            Backlog backlog = backlogMapper.selectByPrimaryKey(id);
            backlog.setState(BacklogState.pending);
            backlogMapper.updateByPrimaryKey(backlog);
            sendEmail(backlog, "backlog will redo!", "sir,the backlog will redo!");
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    private void sendEmail(Backlog backlog, String subject, String content) {
        List<Cooperator> cooperators = backlog.getCooperators();
        cooperators.forEach(cooperator -> {
            try {
                emailService.sendEmail(cooperator.getEmail(),subject,content);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
