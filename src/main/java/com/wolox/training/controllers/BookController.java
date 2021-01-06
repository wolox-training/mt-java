package com.wolox.training.controllers;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.models.Book;
import com.wolox.training.services.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/author/{bookAuthor}")
    public ResponseEntity<Book> findByAuthor(@PathVariable String bookAuthor) {
        Book book = bookService.findByAuthor(bookAuthor);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findOne(@PathVariable Long id) {
        Book book = bookService.findOne(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody BookDTO bookDto) {
        Book book = bookService.create(bookDto);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDto, @PathVariable Long id) {
        Book book = bookService.updateBook(bookDto, id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/isbn")
    public ResponseEntity<Book> findByIsbn(@RequestParam String isbn) {
        Optional<Book> bookOptional = bookService.findByISBN(isbn);

        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(bookService.findByIsbnExternalApi(isbn),
                    HttpStatus.CREATED);
        }

        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/bookCollection")
    public ResponseEntity<List<Book>> findByPublisherAndGenreAndYear(
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String year) {
        return new ResponseEntity<>(
                bookService.findByPublisherAndGenreAndYear(publisher, genre, year), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll(
            @RequestParam(required = false, defaultValue = "") String publisher,
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false, defaultValue = "") String year,
            @RequestParam(required = false, defaultValue = "") String image,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String subtitle,
            @RequestParam(required = false, defaultValue = "0") Integer pages,
            @RequestParam(required = false, defaultValue = "") String isbn) {
        return new ResponseEntity<>(
                bookService
                        .findAllBooks(publisher, genre, year, author, image, title, subtitle, pages,
                                isbn), HttpStatus.OK);
    }


}
