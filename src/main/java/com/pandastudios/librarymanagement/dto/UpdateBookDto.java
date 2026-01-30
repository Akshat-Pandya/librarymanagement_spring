package com.pandastudios.librarymanagement.dto;

import lombok.Data;

@Data
public class UpdateBookDto {
    private String title;
    private String author;
    private Integer totalCopies;
}

