package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Test Author", "British", 1960, "Bio.");
        author.setId(10L);
        book = new Book("Test Book", "ISBN-XYZ", 2020, "Fiction", new BigDecimal("9.99"), author);
        book.setId(1L);
    }

    @Test
    @DisplayName("findAll delegates to repository")
    void testFindAll() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        assertThat(bookService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("save should throw DataIntegrityViolationException on duplicate ISBN")
    void testSaveDuplicateIsbn() {
        when(bookRepository.existsByIsbn("ISBN-XYZ")).thenReturn(true);
        Book newBook = new Book("Another Book", "ISBN-XYZ", 2021, "Drama",
                new BigDecimal("8.00"), author);
        assertThatThrownBy(() -> bookService.save(newBook))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ISBN-XYZ");
    }

    @Test
    @DisplayName("save should succeed with unique ISBN")
    void testSaveSuccess() {
        when(bookRepository.existsByIsbn("ISBN-XYZ")).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);
        Book saved = bookService.save(book);
        assertThat(saved.getTitle()).isEqualTo("Test Book");
    }

    @Test
    @DisplayName("update should throw when book not found")
    void testUpdateNotFound() {
        when(bookRepository.findByIsbn("ISBN-XYZ")).thenReturn(Optional.empty());
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> bookService.update(book))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("findAllBooksWithAuthorDetails delegates to inner-join query")
    void testFindAllBooksWithAuthorDetails() {
        BookAuthorDTO dto = new BookAuthorDTO(1L, "Test Book", "ISBN-XYZ",
                2020, "Fiction", new BigDecimal("9.99"), 10L, "Test Author", "British");
        when(bookRepository.findAllBooksWithAuthorDetails()).thenReturn(List.of(dto));
        List<BookAuthorDTO> result = bookService.findAllBooksWithAuthorDetails();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("Test Author");
    }
}
