package com.pandastudios.librarymanagement.dto;

import java.time.LocalDate;

import com.pandastudios.librarymanagement.entity.BorrowStatus;

import lombok.Data;

@Data
public class BorrowRecordResponseDto {
    private Long recordId;
    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private BorrowStatus status;
}
