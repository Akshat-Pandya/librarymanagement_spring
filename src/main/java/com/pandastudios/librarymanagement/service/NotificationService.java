package com.pandastudios.librarymanagement.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {
    
    public void receiveNotification() {
        log.info("Received notification");
    }
}
