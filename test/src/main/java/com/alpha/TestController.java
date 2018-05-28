package com.alpha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @Autowired
    private Message message;
    @RequestMapping(value = "me")
    public String test(@RequestParam String name) {
        System.out.println("me  controller is taking affect.....");
        return "me";
    }
    @RequestMapping(value = "you")
    public String test(){
        System.out.println("you  controller is taking affect.....");
        return "you";
    }

    @RequestMapping(value = "send")
    public void  sendMessage(){
        message.foo();
    }

}
