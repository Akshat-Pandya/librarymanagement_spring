package com.pandastudios.librarymanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pandastudios.librarymanagement.dto.BorrowBookDto;
import com.pandastudios.librarymanagement.dto.BorrowRecordResponseDto;
import com.pandastudios.librarymanagement.dto.ReturnBookDto;
import com.pandastudios.librarymanagement.entity.Book;
import com.pandastudios.librarymanagement.entity.BorrowStatus;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.entity.enums.BorrowRecord;
import com.pandastudios.librarymanagement.repository.BorrowRecordRepository;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final UserService userService;
    private final BookService bookService;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, UserService userService,
            BookService bookService) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public BorrowRecordResponseDto borrowBook(BorrowBookDto dto,User currUser) {
        Book book = bookService.getBookById(dto.getBookId());

        BorrowRecord record = new BorrowRecord();
        record.setUser(currUser);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setStatus(BorrowStatus.BORROWED);

        bookService.decreaseAvailableCopies(book.getId());
        borrowRecordRepository.save(record);

        return mapToDto(record);
    }

    public BorrowRecordResponseDto returnBook(ReturnBookDto dto, User currUser) {
        BorrowRecord record = borrowRecordRepository.findById(dto.getRecordId()).orElse(null);
        if(record == null || record.getStatus() == BorrowStatus.RETURNED || !record.getUser().getId().equals(currUser.getId())) return null;

        record.setStatus(BorrowStatus.RETURNED);
        record.setReturnDate(LocalDate.now());
        bookService.increaseAvailableCopies(record.getBook().getId());

        borrowRecordRepository.save(record);
        return mapToDto(record);
    }

    public List<BorrowRecordResponseDto> getAllBorrowRecords() {
        return borrowRecordRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public List<BorrowRecordResponseDto> getRecordsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return user != null ? borrowRecordRepository.findAllByUser(user).stream().map(this::mapToDto).toList() : List.of();
    }

    public List<BorrowRecordResponseDto> getRecordsByBook(Long bookId) {
        Book book = bookService.getBookById(bookId);
        return book != null ? borrowRecordRepository.findAllByBook(book).stream().map(this::mapToDto).toList() : List.of();
    }

    public List<BorrowRecordResponseDto> getCurrentUserRecords(User currUser) {
        return currUser != null ? borrowRecordRepository.findAllByUser(currUser).stream().map(this::mapToDto).toList() : List.of();
    }

    private BorrowRecordResponseDto mapToDto(BorrowRecord record) {
        BorrowRecordResponseDto dto = new BorrowRecordResponseDto();
        dto.setRecordId(record.getId());
        dto.setUserId(record.getUser().getId());
        dto.setUserName(record.getUser().getName());
        dto.setBookId(record.getBook().getId());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        return dto;
    }

}
