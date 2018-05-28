package com.alpha;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Message {
    @Async
    public void sendMessage(){
        System.out.println("begin send..."+ Thread.currentThread().getId());
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send.......");
    }
    public void foo(){
        System.out.println("begin send..."+ Thread.currentThread().getId());
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send.......");
    }
}
