package com.example.demo.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Constants;


@Component
public class Scheduler {

    private SimpMessagingTemplate template;
    
    @Autowired
    public Scheduler(SimpMessagingTemplate template) {
        this.template = template;
    }
    
    @Scheduled(fixedRate=1000,initialDelay = 5000)
    public void sendServerTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.template.convertAndSend(Constants.SIMPLE_BROKER_SERVER_TIME, "{\"servertime\":\"" + dateTime.format(dtf) + " EET\"}");
        System.out.println("Running schedule job");
    }
}
