package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        savedAuthor = authorRepository.save(
                new Author("Test Author", "Indian", 1980, "A test biography."));
    }

    @Test
    @DisplayName("Should save and retrieve an author by ID")
    void testSaveAndFindById() {
        Optional<Author> found = authorRepository.findById(savedAuthor.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName("findByNameIgnoreCase should be case-insensitive")
    void testFindByNameIgnoreCase() {
        Optional<Author> found = authorRepository.findByNameIgnoreCase("test author");
        assertThat(found).isPresent();
        assertThat(found.get().getNationality()).isEqualTo("Indian");
    }

    @Test
    @DisplayName("existsByNameIgnoreCase should return true for existing name")
    void testExistsByName() {
        assertThat(authorRepository.existsByNameIgnoreCase("TEST AUTHOR")).isTrue();
        assertThat(authorRepository.existsByNameIgnoreCase("Nobody")).isFalse();
    }

    @Test
    @DisplayName("findAuthorsWithBooks should only return authors who have books")
    void testFindAuthorsWithBooks() {
        Author authorWithoutBook = authorRepository.save(
                new Author("Lonely Writer", "French", 1970, "Has no books."));

        Book book = new Book("Sample Book", "111-1", 2020, "Fiction",
                new BigDecimal("9.99"), savedAuthor);
        bookRepository.save(book);

        List<Author> authorsWithBooks = authorRepository.findAuthorsWithBooks();
        assertThat(authorsWithBooks).hasSize(1);
        assertThat(authorsWithBooks.get(0).getId()).isEqualTo(savedAuthor.getId());
    }

    @Test
    @DisplayName("findByNationality should filter correctly")
    void testFindByNationality() {
        authorRepository.save(new Author("Another Writer", "Indian", 1990, "Also Indian."));
        List<Author> indianAuthors = authorRepository.findByNationality("Indian");
        assertThat(indianAuthors).hasSize(2);
    }

    @Test
    @DisplayName("Should delete an author")
    void testDeleteAuthor() {
        Long id = savedAuthor.getId();
        authorRepository.deleteById(id);
        assertThat(authorRepository.findById(id)).isEmpty();
    }
}
