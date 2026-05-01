package com.library.service;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DataIntegrityViolationException(
                    "A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        Optional<Book> existing = bookRepository.findByIsbn(book.getIsbn());
        // Allow same ISBN if it's the same book being updated
        if (existing.isPresent() && !existing.get().getId().equals(book.getId())) {
            throw new DataIntegrityViolationException(
                    "Another book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        if (!bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book not found with id: " + book.getId());
        }
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookAuthorDTO> findAllBooksWithAuthorDetails() {
        return bookRepository.findAllBooksWithAuthorDetails();
    }

    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
}
