package com.pandastudios.librarymanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowBookDto {
    @NotNull(message = "Book ID is required")
    private Long bookId;
}