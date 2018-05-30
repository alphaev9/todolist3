package com.alpha.web.controller;

import com.alpha.repository.BacklogRepository;
import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.BacklogState;
import com.alpha.repository.entity.Cooperator;
import com.alpha.repository.mongodb.MongoID;
import com.alpha.repository.rdb.singleTable.RdbId;
import com.alpha.service.EmailService;
import org.bson.types.ObjectId;
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
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "memo", method = RequestMethod.POST)
    public ModelAndView memo(Backlog backlog, @RequestParam("attachmentFile") MultipartFile file, @ModelAttribute("userId") PersistId userId) {
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
        userRepository.addBacklog(userId, backlog);
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = {"/removePending", "/removeFinished"})
    public ModelAndView remove(@RequestParam("backlogId") List<PersistId> ids, @ModelAttribute("userId") PersistId userId) {
        /**************
         * 如何对数组做转型
         * */
        for (PersistId id : ids) {
//            RdbId rdbId = new RdbId(Integer.valueOf(id));
//            MongoID mongoID = new MongoID(new ObjectId(id));
            Backlog backlog = backlogRepository.getBacklogById(id);
            String attachment = backlog.getAttachment();
            if (attachment != null) {
                String root = servletContext.getRealPath("/");
                new File(root + "/" + attachment).delete();
            }
            userRepository.removeBacklog(userId, id);
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }


    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public ModelAndView finish(@RequestParam("backlogId") List<PersistId> ids) {
        for (PersistId id : ids) {
//            RdbId rdbId = new RdbId(Integer.valueOf(id));
//            MongoID mongoID = new MongoID(new ObjectId(id));
            Backlog backlog = backlogRepository.getBacklogById(id);
            backlog.setState(BacklogState.finished);
            backlogRepository.updateBacklog(backlog);
            sendEmail(backlog, "backlog is finished!", "sir,the backlog is finished!");
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    /************************************
     * 前端传递数组时，用list来接受，可以自动转型
     *********************************/
    @RequestMapping(value = "redo", method = RequestMethod.POST)
    public ModelAndView redo(@RequestParam("backlogId") List<PersistId> ids) {
        for (PersistId id : ids) {
//            RdbId rdbId = new RdbId(Integer.valueOf(id));
//            MongoID mongoID = new MongoID(new ObjectId(id));
            Backlog backlog = backlogRepository.getBacklogById(id);
            backlog.setState(BacklogState.pending);
            backlogRepository.updateBacklog(backlog);
            sendEmail(backlog, "backlog will redo!", "sir,the backlog will redo!");
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    private void sendEmail(Backlog backlog, String subject, String content) {
        List<Cooperator> cooperators = backlog.getCooperators();
        cooperators.forEach(cooperator -> {
            try {
                emailService.sendEmail(cooperator.getEmail(), subject, content);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
