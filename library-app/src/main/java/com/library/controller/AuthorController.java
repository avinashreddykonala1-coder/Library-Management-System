package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // ── List all authors ──────────────────────────────────────────────────────
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("pageTitle", "All Authors");
        return "authors/list";
    }

    // ── Show add form ─────────────────────────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Add Author");
        return "authors/form";
    }

    // ── Handle add form submission ────────────────────────────────────────────
    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Author");
            return "authors/form";
        }
        try {
            authorService.save(author);
            redirectAttrs.addFlashAttribute("successMessage", "Author added successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Add Author");
            return "authors/form";
        }
        return "redirect:/authors";
    }

    // ── Show edit form ────────────────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        return authorService.findById(id).map(author -> {
            model.addAttribute("author", author);
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Author not found.");
            return "redirect:/authors";
        });
    }

    // ── Handle edit form submission ───────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttrs) {
        author.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        try {
            authorService.update(author);
            redirectAttrs.addFlashAttribute("successMessage", "Author updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        return "redirect:/authors";
    }
}
