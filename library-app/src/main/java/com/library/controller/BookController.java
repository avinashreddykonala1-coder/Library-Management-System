package com.library.controller;

import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // ── List all books (inner join view) ──────────────────────────────────────
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("bookDetails", bookService.findAllBooksWithAuthorDetails());
        model.addAttribute("pageTitle", "All Books");
        return "books/list";
    }

    // ── Show add form ─────────────────────────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("pageTitle", "Add Book");
        return "books/form";
    }

    // ── Handle add submission ─────────────────────────────────────────────────
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult result,
                          @RequestParam("authorId") Long authorId,
                          Model model,
                          RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Add Book");
            return "books/form";
        }
        authorService.findById(authorId).ifPresent(book::setAuthor);
        try {
            bookService.save(book);
            redirectAttrs.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Add Book");
            return "books/form";
        }
        return "redirect:/books";
    }

    // ── Show edit form ────────────────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        return bookService.findById(id).map(book -> {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Book not found.");
            return "redirect:/books";
        });
    }

    // ── Handle edit submission ────────────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             @RequestParam("authorId") Long authorId,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        book.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }
        authorService.findById(authorId).ifPresent(book::setAuthor);
        try {
            bookService.update(book);
            redirectAttrs.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }
        return "redirect:/books";
    }
}
