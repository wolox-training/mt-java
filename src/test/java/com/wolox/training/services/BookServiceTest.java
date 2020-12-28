package com.wolox.training.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.models.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService mockService;

    private Book oneTestBook;

    private BookDTO dto;

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
        dto = new BookDTO();
        dto.setTitle("IT");
        dto.setPages(234);
        dto.setPublisher("Viking Press");
        dto.setIsbn("11112222333");
        dto.setImage("image2.png");
        dto.setGenre("terror");
        dto.setAuthor("Stephen King");
        dto.setSubtitle("-");
        dto.setYear("1986");
    }

    @Test
    public void whenCreateBook_thenBookIsPersisted(){
        Mockito.when(bookRepository.save(oneTestBook)).thenReturn(oneTestBook);

        assertNotNull(mockService.create(dto));
    }

    @Test
    public void testingGuavaPreconditions(){
        dto.setAuthor(null);
        Assertions.assertThrows(NullPointerException.class, ()-> mockService.create(dto));
    }

    @Test
    public void testingFindOne(){
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(oneTestBook));
        assertEquals(oneTestBook, mockService.findOne(1L));
    }
}
