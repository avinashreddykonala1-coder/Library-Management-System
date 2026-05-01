package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        if (authorRepository.existsByNameIgnoreCase(author.getName())
                && (author.getId() == null)) {
            throw new IllegalArgumentException("An author with this name already exists.");
        }
        return authorRepository.save(author);
    }

    public Author update(Author author) {
        if (!authorRepository.existsById(author.getId())) {
            throw new IllegalArgumentException("Author not found with id: " + author.getId());
        }
        return authorRepository.save(author);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> findAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }
}
