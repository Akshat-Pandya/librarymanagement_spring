package com.pandastudios.librarymanagement.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {
    
    @RabbitListener(queues = "return-notification-queue")
    public void receiveNotification() {
        log.info("Received notification");
    }
}
