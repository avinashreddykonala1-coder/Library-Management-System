package com.library.entity;

import java.math.BigDecimal;

/**
 * DTO used to hold the result of the inner join between books and authors.
 */
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String isbn;
    private Integer publishedYear;
    private String genre;
    private BigDecimal price;
    private Long authorId;
    private String authorName;
    private String nationality;

    public BookAuthorDTO(Long bookId, String bookTitle, String isbn,
                         Integer publishedYear, String genre, BigDecimal price,
                         Long authorId, String authorName, String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.price = price;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
    }

    // Getters
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getIsbn() { return isbn; }
    public Integer getPublishedYear() { return publishedYear; }
    public String getGenre() { return genre; }
    public BigDecimal getPrice() { return price; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getNationality() { return nationality; }
}
