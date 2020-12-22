package com.wolox.training.controllers;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BookController.class)
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService mockService;

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
    @WithMockUser
    public void whenFindByIdWhichExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockService.findOne(1L)).thenReturn(oneTestBook);
        String url = "/api/books/1";
        mvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\n"
                        + "    \"id\": 0,\n"
                        + "    \"genre\": \"terror\",\n"
                        + "    \"author\": \"Stephen King\",\n"
                        + "    \"image\": \"image2.png\",\n"
                        + "    \"title\": \"IT\",\n"
                        + "    \"subtitle\": \"-\",\n"
                        + "    \"publisher\": \"Viking Press\",\n"
                        + "    \"year\": \"1986\",\n"
                        + "    \"pages\": 234,\n"
                        + "    \"isbn\": \"11112222333\"\n"
                        + "}"));
    }

    @Test
    public void whenFindByIdWhichNoExists() throws Exception {
        Mockito.when(mockService.findOne(1L)).thenThrow(ObjectNotFoundException.class);
    }

    @Test
    public void testingGuavaPreconditions(){
        BookDTO dto = new BookDTO();
        dto.setTitle(null);
        dto.setPages(234);
        dto.setPublisher("Viking Press");
        dto.setIsbn("11112222333");
        dto.setImage("image2.png");
        dto.setGenre("terror");
        dto.setAuthor("Stephen King");
        dto.setSubtitle("-");
        dto.setYear("1986");
        Mockito.when(mockService.create(dto)).thenThrow(NullPointerException.class);
    }

    @Test
    public void testingUniqueValueDB(){
        BookDTO dto = new BookDTO();
        dto.setTitle("IT");
        dto.setPages(234);
        dto.setPublisher("Viking Press");
        dto.setIsbn("11112222333");
        dto.setImage("image2.png");
        dto.setGenre("terror");
        dto.setAuthor("Stephen King");
        dto.setSubtitle("-");
        dto.setYear("1986");
        mockService.create(dto);
        Mockito.when(mockService.create(dto)).thenThrow(DataIntegrityViolationException.class);
    }

    @Test
    public void testingGetAllBooks_WithoutAuth() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/books/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
