package com.pandastudios.librarymanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnBookDto {
    @NotNull(message = "Borrow record ID is required")
    private Long recordId;
}
