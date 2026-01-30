package com.pandastudios.librarymanagement.entity.enums;

import java.time.LocalDate;

import com.pandastudios.librarymanagement.entity.Book;
import com.pandastudios.librarymanagement.entity.BorrowStatus;
import com.pandastudios.librarymanagement.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // there may be many records for the same user so many-to-one

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // there may be many records for the same book so many-to-one

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status;

}