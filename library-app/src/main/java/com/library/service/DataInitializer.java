package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (authorRepository.count() > 0) return; // Prevent re-seeding

        // ── 10 Authors ──────────────────────────────────────────────────────────
        Author a1  = authorRepository.save(new Author("George Orwell",        "British",    1903, "Eric Arthur Blair, known by his pen name George Orwell, was an English novelist and essayist."));
        Author a2  = authorRepository.save(new Author("J.K. Rowling",          "British",    1965, "Best known for writing the Harry Potter fantasy series."));
        Author a3  = authorRepository.save(new Author("Haruki Murakami",        "Japanese",   1949, "Japanese writer whose books and stories have been bestsellers in Japan and internationally."));
        Author a4  = authorRepository.save(new Author("Gabriel García Márquez", "Colombian",  1927, "Colombian novelist and Nobel Prize winner, known for magical realism."));
        Author a5  = authorRepository.save(new Author("Toni Morrison",          "American",   1931, "Nobel Prize-winning American novelist, known for depicting the African-American experience."));
        Author a6  = authorRepository.save(new Author("Leo Tolstoy",            "Russian",    1828, "Russian writer widely regarded as one of the greatest authors of all time."));
        Author a7  = authorRepository.save(new Author("Jane Austen",            "British",    1775, "English novelist known primarily for her six major novels, which interpret, critique and comment upon the British landed gentry."));
        Author a8  = authorRepository.save(new Author("Fyodor Dostoevsky",      "Russian",    1821, "Russian novelist, short story writer, essayist and journalist."));
        Author a9  = authorRepository.save(new Author("Chimamanda Ngozi Adichie","Nigerian",  1977, "Nigerian author of novels, short stories, and nonfiction, known for her feminist perspective."));
        Author a10 = authorRepository.save(new Author("Kazuo Ishiguro",         "British",    1954, "Nobel Prize-winning British novelist born in Nagasaki, Japan."));

        // ── 10 Books ─────────────────────────────────────────────────────────────
        bookRepository.saveAll(List.of(
            new Book("Nineteen Eighty-Four",         "978-0-452-28423-4", 1949, "Dystopian",   new BigDecimal("9.99"),  a1),
            new Book("Harry Potter and the Philosopher's Stone", "978-0-7475-3269-9", 1997, "Fantasy", new BigDecimal("12.99"), a2),
            new Book("Norwegian Wood",               "978-0-37-570402-3", 1987, "Literary",    new BigDecimal("11.99"), a3),
            new Book("One Hundred Years of Solitude","978-0-06-088328-7", 1967, "Magical Realism", new BigDecimal("14.99"), a4),
            new Book("Beloved",                      "978-1-40-038854-4", 1987, "Historical",  new BigDecimal("10.99"), a5),
            new Book("War and Peace",                "978-0-14-303943-3", 1869, "Historical",  new BigDecimal("16.99"), a6),
            new Book("Pride and Prejudice",          "978-0-14-143951-8", 1813, "Romance",     new BigDecimal("7.99"),  a7),
            new Book("Crime and Punishment",         "978-0-14-305814-4", 1866, "Psychological", new BigDecimal("9.49"), a8),
            new Book("Purple Hibiscus",              "978-1-61-699078-5", 2003, "Literary",    new BigDecimal("10.49"), a9),
            new Book("The Remains of the Day",       "978-0-67-973172-7", 1989, "Literary",    new BigDecimal("11.49"), a10)
        ));

        System.out.println("✅ Database seeded: 10 authors and 10 books loaded.");
    }
}
