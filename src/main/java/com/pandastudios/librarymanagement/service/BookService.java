package com.pandastudios.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pandastudios.librarymanagement.dto.CreateBookDto;
import com.pandastudios.librarymanagement.dto.UpdateBookDto;
import com.pandastudios.librarymanagement.entity.Book;
import com.pandastudios.librarymanagement.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(CreateBookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setTotalCopies(bookDto.getTotalCopies());
        book.setAvailableCopies(bookDto.getTotalCopies());
        bookRepository.save(book);
        return book;
    }

    public Book updateBook(Long id, UpdateBookDto bookDto) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            System.out.println("Book not found.");
            return null; // Or throw a BookNotFoundException
        }
        if(bookDto.getTitle() != null && !bookDto.getTitle().isEmpty()) {
            book.setTitle(bookDto.getTitle());
        }
        if(bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty()) {
            book.setAuthor(bookDto.getAuthor());
        }
        if(bookDto.getTotalCopies() != null) {
            int diff = bookDto.getTotalCopies() - book.getTotalCopies();
            int newAvailable = book.getAvailableCopies() + diff;
            book.setAvailableCopies(Math.min(book.getTotalCopies(), Math.max(0, newAvailable)));// Prevents negative available copies
            book.setTotalCopies(bookDto.getTotalCopies());
        }
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public Book searchByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    public void decreaseAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }
    }

    public void increaseAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }
    }
}
