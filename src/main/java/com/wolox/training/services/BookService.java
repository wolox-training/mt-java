package com.wolox.training.services;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookService {

    public static final String BOOK = "Book";

    @Autowired
    private BookRepository bookRepository;

    /**
     * This method returns a list of {@link Book}
     *
     * @return the list of {@link Book} persisted on db
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * This method returns a {@link Book} wrote by a specific Author
     *
     * @param bookAuthor: name of the book's author
     * @return a {@link Book} wrote by a bookAuthor
     */
    public Optional<Book> findByAuthor(String bookAuthor) {
        return bookRepository.findByAuthor(bookAuthor);
    }

    /**
     * This method returns a {@link Book} by id
     *
     * @param id: book's id
     * @return a {@link Book} by id
     */
    public Book findOne(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(BOOK));
    }


    /**
     * This method creates a {@link Book}
     *
     * @param bookDto: Data Transfer Object of {@link Book}
     * @return the {@link Book} created
     */
    @Transactional
    public Book create(BookDTO bookDto) {
        Book book = new Book();
        adaptBookDtoToBookModel(bookDto, book);
        return bookRepository.save(book);
    }

    /**
     * This method deletes a {@link Book}
     *
     * @param id: book's id
     */
    @Transactional
    public void delete(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(BOOK));
        bookRepository.deleteById(id);
    }

    /**
     * This method updates an specific {@link Book}
     *
     * @param bookDto: Data Transfer Object of {@link Book} with the new values
     * @param id:      book's id
     * @return the {@link Book} updated
     */
    @Transactional
    public Book updateBook(BookDTO bookDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(BOOK));

        adaptBookDtoToBookModel(bookDto, book);

        return bookRepository.save(book);
    }

    private void adaptBookDtoToBookModel(BookDTO bookDto, Book book) {
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
