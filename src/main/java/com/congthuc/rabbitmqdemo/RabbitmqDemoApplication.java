package com.congthuc.rabbitmqdemo;

import com.congthuc.rabbitmqdemo.service.NotificationProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDemoApplication implements CommandLineRunner {
    static final Logger logger = LoggerFactory.getLogger(RabbitmqDemoApplication.class);

    @Autowired
    private NotificationProducerService notificationProducerService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info(" producer application is up and running");
        notificationProducerService.produce();
    }

}
