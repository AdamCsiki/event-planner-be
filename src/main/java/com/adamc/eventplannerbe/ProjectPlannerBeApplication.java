package com.adamc.eventplannerbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
public class ProjectPlannerBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectPlannerBeApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduledRun() {
        System.out.println("Current scheduled time: " + new Date());
    }

}
