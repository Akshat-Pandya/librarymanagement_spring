package com.pandastudios.librarymanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.pandastudios.librarymanagement.config.RabbitConfig;
import com.pandastudios.librarymanagement.entity.AuditLog;
import com.pandastudios.librarymanagement.repository.LoggingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoggingService {

    private final LoggingRepository loggingRepository;

    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    public List<AuditLog> getAllLogs() {
        return loggingRepository.findAll();
    }

    @RabbitListener(queues={"borrow-logging-queue", "return-logging-queue"})
    public void saveLog(AuditLog logMessage) {
        log.info("Saving log: Action - {}, Message - {}", logMessage.getAction(), logMessage.getMessage()); 
        loggingRepository.save(logMessage);
    }

    public List<AuditLog> findByActionKeyword(String keyword) {
        return loggingRepository.findByActionContainingIgnoreCase(keyword);
    }

    public List<AuditLog> findByMessageKeyword(String keyword) {
        return loggingRepository.findByMessageContainingIgnoreCase(keyword);
    }

    public List<AuditLog> findByActionOrMessageKeyword(String keyword) {
        return loggingRepository.findByActionContainingIgnoreCaseOrMessageContainingIgnoreCase(keyword, keyword);
    }

    public List<AuditLog> findByMessageKeywordAndAfter(String keyword, LocalDateTime fromDate) {
        return loggingRepository.findByMessageContainingIgnoreCaseAndCreatedAtAfter(keyword, fromDate);
    }

    public List<AuditLog> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return loggingRepository.findByCreatedAtBetween(from, to);
    }
}
