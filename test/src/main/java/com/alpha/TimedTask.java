package com.alpha;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimedTask {
    @Scheduled(fixedDelay = 5_000L,initialDelay = 2_000L)
    public void test(){
        System.out.println("timed task is working.....");
    }


}
