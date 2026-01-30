package com.pandastudios.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pandastudios.librarymanagement.entity.Book;
import com.pandastudios.librarymanagement.entity.BorrowStatus;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.entity.enums.BorrowRecord;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findAllByUser(User user);

    List<BorrowRecord> findAllByBook(Book book);

    List<BorrowRecord> findAllByStatus(BorrowStatus status);

    List<BorrowRecord> findAllByUserAndStatus(User user, BorrowStatus status);
}
