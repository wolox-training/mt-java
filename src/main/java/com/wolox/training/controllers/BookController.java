package com.wolox.training.controllers;

import com.wolox.training.dtos.BookDto;
import com.wolox.training.models.Book;
import com.wolox.training.services.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "Woloxer") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping(value = "/book/{bookAuthor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findByAuthor(@PathVariable String bookAuthor) {
        return bookService.findByAuthor(bookAuthor);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findOne(@PathVariable Long id) {
        return bookService.findOne(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        return bookService.updateBook(bookDto, id);
    }

}
