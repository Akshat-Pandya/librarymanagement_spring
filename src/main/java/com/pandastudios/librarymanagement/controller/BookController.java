package com.pandastudios.librarymanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.dto.CreateBookDto;
import com.pandastudios.librarymanagement.dto.UpdateBookDto;
import com.pandastudios.librarymanagement.entity.Book;
import com.pandastudios.librarymanagement.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Admin Controlled
    @PostMapping("/add")
    public Book addBook(@RequestBody CreateBookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    // Admin Controlled
    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody UpdateBookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    // Admin Controlled
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("/isbn")
    public Book searchByIsbn(@RequestParam String isbn) {
        return bookService.searchByIsbn(isbn);
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }
}

