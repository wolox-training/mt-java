package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book oneTestBook;

    @BeforeEach
    public void setUp() {
        oneTestBook = new Book();
        oneTestBook.setTitle("IT");
        oneTestBook.setPages(234);
        oneTestBook.setPublisher("Viking Press");
        oneTestBook.setIsbn("11112222333");
        oneTestBook.setImage("image2.png");
        oneTestBook.setGenre("terror");
        oneTestBook.setAuthor("Stephen King");
        oneTestBook.setSubtitle("-");
        oneTestBook.setYear("1986");
    }

    @Test
    public void whenCreateBook_thenBookIsPersisted(){
        Book persistedBook = bookRepository.save(oneTestBook);
        assertNotNull(persistedBook);
    }

    @Test
    public void testingGuavaPreconditions(){
       Assertions.assertThrows(NullPointerException.class, ()-> oneTestBook.setAuthor(null));
    }

    @Test
    public void testingRepeatingUniqueValueDB(){
        bookRepository.save(oneTestBook);
        Book otherBook = new Book();
        otherBook.setTitle("IT");
        otherBook.setPages(234);
        otherBook.setPublisher("Viking Press");
        otherBook.setIsbn("11112222333");
        otherBook.setImage("image2.png");
        otherBook.setGenre("terror");
        otherBook.setAuthor("Stephen King");
        otherBook.setSubtitle("-");
        otherBook.setYear("1986");
        Assertions.assertThrows(DataIntegrityViolationException.class, ()-> bookRepository.save(otherBook));
    }

    @Test
    public void testingSaveObjectWithNullValues(){
        Book otherBook = new Book();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> bookRepository.save(otherBook));
    }

    @Test
    public void testingFindByPublisherAndGenreAndYearMethod(){
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findByPublisherAndGenreAndYear("Viking Press", "terror", "1986");
        assertThat(books, is(not(empty())));
    }

}
