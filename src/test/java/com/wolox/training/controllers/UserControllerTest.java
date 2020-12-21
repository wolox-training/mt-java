package com.wolox.training.controllers;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Users;
import com.wolox.training.services.UserService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService mockService;

    private Users oneTestUser;

    @BeforeEach
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("torsello");
        oneTestUser.setPassword("123456");
        oneTestUser.setName("Matias Torsello");
        oneTestUser.setBirthdate(LocalDate.of(1995,10,11));
    }

    @Test
    public void whenFindByIdWhichExists_thenUserIsReturned() throws Exception {
        Mockito.when(mockService.findOne(1L)).thenReturn(oneTestUser);
        String url = "/api/users/1";
        mvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\n"
                        + "    \"id\": 0,\n"
                        + "    \"username\": \"torsello\",\n"
                        + "    \"password\": \"123456\",\n"
                        + "    \"name\": \"Matias Torsello\",\n"
                        + "    \"birthdate\": \"1995-10-11\",\n"
                        + "    \"books\": []\n"
                        + "}"));
    }

    @Test
    public void testingGuavaPreconditions(){
        UserDTO dto = new UserDTO();
        dto.setUsername(null);
        dto.setName("Matias Torsello");
        dto.setBirthdate(LocalDate.of(1995,10,11));
        Mockito.when(mockService.create(dto)).thenThrow(NullPointerException.class);
    }

    @Test
    public void whenFindByIdWhichNoExists() throws Exception {
        Mockito.when(mockService.findOne(1L)).thenThrow(ObjectNotFoundException.class);
    }

}
