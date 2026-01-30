package com.pandastudios.librarymanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.entity.AuditLog;
import com.pandastudios.librarymanagement.service.LoggingService;

@RestController
@RequestMapping("/logs")
public class LoggingController {

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/all")
    public List<AuditLog> getAllLogs() {
        return loggingService.getAllLogs();
    }

    @GetMapping("/action")
    public List<AuditLog> getByActionKeyword(@RequestParam String keyword) {
        return loggingService.findByActionKeyword(keyword);
    }

    @GetMapping("/message")
    public List<AuditLog> getByMessageKeyword(@RequestParam String keyword) {
        return loggingService.findByMessageKeyword(keyword);
    }

    @GetMapping("/search")
    public List<AuditLog> getByActionOrMessageKeyword(@RequestParam String keyword) {
        return loggingService.findByActionOrMessageKeyword(keyword);
    }

    @GetMapping("/message-after")
    public List<AuditLog> getByMessageAndAfter(
            @RequestParam String keyword,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate) {
        return loggingService.findByMessageKeywordAndAfter(keyword, fromDate);
    }

    @GetMapping("/date-range")
    public List<AuditLog> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return loggingService.findByDateRange(from, to);
    }
}
