package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        author = authorRepository.save(new Author("Test Author", "British", 1960, "Bio."));
        bookRepository.save(new Book("Book One", "ISBN-001", 2000, "Drama",
                new BigDecimal("12.00"), author));
        bookRepository.save(new Book("Book Two", "ISBN-002", 2010, "Thriller",
                new BigDecimal("15.00"), author));
    }

    @Test
    @DisplayName("findAllBooksWithAuthorDetails should return correct inner-join DTOs")
    void testFindAllBooksWithAuthorDetails() {
        List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthorDetails();
        assertThat(results).hasSize(2);
        BookAuthorDTO first = results.get(0);
        assertThat(first.getAuthorName()).isEqualTo("Test Author");
        assertThat(first.getNationality()).isEqualTo("British");
    }

    @Test
    @DisplayName("existsByIsbn should correctly detect duplicates")
    void testExistsByIsbn() {
        assertThat(bookRepository.existsByIsbn("ISBN-001")).isTrue();
        assertThat(bookRepository.existsByIsbn("ISBN-999")).isFalse();
    }

    @Test
    @DisplayName("findByAuthorId should return only that author's books")
    void testFindByAuthorId() {
        Author other = authorRepository.save(new Author("Other", "French", 1970, "."));
        bookRepository.save(new Book("Other Book", "ISBN-003", 2020, "Mystery",
                new BigDecimal("10.00"), other));

        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
        books.forEach(b -> assertThat(b.getAuthor().getId()).isEqualTo(author.getId()));
    }

    @Test
    @DisplayName("findByIsbn should return the correct book")
    void testFindByIsbn() {
        assertThat(bookRepository.findByIsbn("ISBN-002")).isPresent();
        assertThat(bookRepository.findByIsbn("NONE")).isEmpty();
    }
}
