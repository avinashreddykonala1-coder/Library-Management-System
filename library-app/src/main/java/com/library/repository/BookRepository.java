package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Check for duplicate ISBN
    boolean existsByIsbn(String isbn);

    // Find by genre
    List<Book> findByGenreIgnoreCase(String genre);

    // Find books by author id
    List<Book> findByAuthorId(Long authorId);

    // Custom JPQL inner join returning DTO — the required custom query method
    @Query("""
        SELECT new com.library.entity.BookAuthorDTO(
            b.id, b.title, b.isbn, b.publishedYear, b.genre, b.price,
            a.id, a.name, a.nationality
        )
        FROM Book b INNER JOIN b.author a
        ORDER BY a.name, b.title
        """)
    List<BookAuthorDTO> findAllBooksWithAuthorDetails();

    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);
}
