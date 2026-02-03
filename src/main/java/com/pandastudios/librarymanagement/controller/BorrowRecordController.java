package com.pandastudios.librarymanagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.dto.BorrowBookDto;
import com.pandastudios.librarymanagement.dto.BorrowRecordResponseDto;
import com.pandastudios.librarymanagement.dto.ReturnBookDto;
import com.pandastudios.librarymanagement.entity.AuditLog;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.service.BorrowRecordService;
import com.pandastudios.librarymanagement.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/borrow")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;


    public BorrowRecordController(BorrowRecordService borrowRecordService, UserService userService, RabbitTemplate rabbitTemplate) {
        this.borrowRecordService = borrowRecordService;
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/borrow-book")
    public BorrowRecordResponseDto borrowBook(@RequestBody @Valid BorrowBookDto dto, Principal principal) {
        User currUser = userService.getCurrentUser(principal);

        var response= borrowRecordService.borrowBook(dto,currUser);
        AuditLog log=new AuditLog();
        log.setAction("Borrow Book Attempt");
        log.setMessage("User "+currUser.getEmail()+" attempted to borrow book with ID "+dto.getBookId()+". Result: "+(response!=null?"Success":"Failure"));

        

        return response;
    }

    @PostMapping("/return-book")
    public BorrowRecordResponseDto returnBook(@RequestBody @Valid ReturnBookDto dto, Principal principal) {
        User currUser = userService.getCurrentUser(principal);

        var response =borrowRecordService.returnBook(dto,currUser);

        AuditLog log=new AuditLog();
        log.setAction("Return Book Attempt");
        log.setMessage("User "+currUser.getEmail()+" attempted to return book corresponding to record ID "+dto.getRecordId()+". Result: "+(response!=null?"Success":"Failure"));

        return response;
    }

    // admin controlled
    @GetMapping("/all")
    public List<BorrowRecordResponseDto> getAllRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    // admin controlled
    @GetMapping("/by-user/{userId}")
    public List<BorrowRecordResponseDto> getRecordsByUser(@PathVariable Long userId) {
        return borrowRecordService.getRecordsByUserId(userId);
    }

    // admin controlled
    @GetMapping("/by-book/{bookId}")
    public List<BorrowRecordResponseDto> getRecordsByBook(@PathVariable Long bookId) {
        return borrowRecordService.getRecordsByBook(bookId);
    }

    @GetMapping("/my-records")
    public List<BorrowRecordResponseDto> getCurrentUserRecords(Principal principal) {
        User currUser = userService.getCurrentUser(principal);
        return borrowRecordService.getCurrentUserRecords(currUser);
    }

}
