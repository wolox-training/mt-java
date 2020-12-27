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

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/book/{bookAuthor}")
    public ResponseEntity<Book> findByAuthor(@PathVariable String bookAuthor) {
        Optional<Book> bookOptional = bookService.findByAuthor(bookAuthor);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
            return new ResponseEntity<>(bookService.findByIsbnExternalApi(isbn), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }


}
