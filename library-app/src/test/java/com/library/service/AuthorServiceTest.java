package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("J.K. Rowling", "British", 1965, "Harry Potter author.");
        author.setId(1L);
    }

    @Test
    @DisplayName("findAll should delegate to repository")
    void testFindAll() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<Author> result = authorService.findAll();
        assertThat(result).hasSize(1);
        verify(authorRepository).findAll();
    }

    @Test
    @DisplayName("findById should return Optional with author when found")
    void testFindById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("J.K. Rowling");
    }

    @Test
    @DisplayName("save should throw when duplicate name is detected on new author")
    void testSaveThrowsOnDuplicate() {
        Author newAuthor = new Author("J.K. Rowling", "British", 1965, "Duplicate.");
        // id is null → new author
        when(authorRepository.existsByNameIgnoreCase("J.K. Rowling")).thenReturn(true);
        assertThatThrownBy(() -> authorService.save(newAuthor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("save should succeed for unique author")
    void testSaveSuccess() {
        Author newAuthor = new Author("New Author", "Indian", 1990, "Bio.");
        when(authorRepository.existsByNameIgnoreCase("New Author")).thenReturn(false);
        when(authorRepository.save(newAuthor)).thenReturn(newAuthor);
        Author saved = authorService.save(newAuthor);
        assertThat(saved).isNotNull();
        verify(authorRepository).save(newAuthor);
    }

    @Test
    @DisplayName("update should throw when author not found")
    void testUpdateNotFound() {
        when(authorRepository.existsById(99L)).thenReturn(false);
        author.setId(99L);
        assertThatThrownBy(() -> authorService.update(author))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update should save when author exists")
    void testUpdateSuccess() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.save(author)).thenReturn(author);
        Author updated = authorService.update(author);
        assertThat(updated.getName()).isEqualTo("J.K. Rowling");
    }
}
