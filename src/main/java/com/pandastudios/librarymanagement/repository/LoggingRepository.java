package com.pandastudios.librarymanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pandastudios.librarymanagement.entity.AuditLog;

public interface LoggingRepository extends JpaRepository<AuditLog, Long> {

    // Find logs containing a keyword in the action
    List<AuditLog> findByActionContainingIgnoreCase(String keyword);

    // Find logs containing a keyword in the message
    List<AuditLog> findByMessageContainingIgnoreCase(String keyword);

    // Find logs containing a keyword in either action or message
    List<AuditLog> findByActionContainingIgnoreCaseOrMessageContainingIgnoreCase(String actionKeyword, String messageKeyword);

    // Optional: Find logs created after a certain date containing a keyword in message
    List<AuditLog> findByMessageContainingIgnoreCaseAndCreatedAtAfter(String keyword, LocalDateTime fromDate);

    // Optional: Find logs created between two dates
    List<AuditLog> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
