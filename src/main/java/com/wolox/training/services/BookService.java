package com.wolox.training.services;

import com.wolox.training.dtos.BookDto;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findByAuthor(String bookAuthor) {
        return bookRepository.findByAuthor(bookAuthor);
    }

    public Book findOne(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book"));
    }

    public Book create(BookDto bookDto) {
        Book book = new Book();
        adaptBookDtoToBookModel(bookDto, book);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book"));
        bookRepository.deleteById(id);
    }

    public Book updateBook(BookDto bookDto, Long id) {
        Book book;

        book = bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book"));

        adaptBookDtoToBookModel(bookDto, book);

        return bookRepository.save(book);
    }

    private void adaptBookDtoToBookModel(BookDto bookDto, Book book) {
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setImage(bookDto.getImage());
        book.setIsbn(bookDto.getIsbn());
        book.setPages(bookDto.getPages());
        book.setPublisher(bookDto.getPublisher());
        book.setSubtitle(bookDto.getSubtitle());
        book.setTitle(bookDto.getTitle());
        book.setYear(bookDto.getYear());
    }
}
