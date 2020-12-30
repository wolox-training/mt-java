package com.wolox.training.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.models.User;
import com.wolox.training.repositories.UserRepository;
import java.time.LocalDate;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private UserService userService;

    private User oneTestUser;

    private UserDTO dto;

    @BeforeEach
    public void setUp() {
        oneTestUser = new User();
        oneTestUser.setUsername("torsello");
        oneTestUser.setName("Matias Torsello");
        oneTestUser.setBirthdate(LocalDate.of(1995,10,11));
        dto = new UserDTO();
        dto.setUsername("torsello");
        dto.setName("Matias Torsello");
        dto.setBirthdate(LocalDate.of(1995,10,11));
    }

    @Test
    public void whenCreateUsers_thenUsersIsPersisted(){
        Mockito.when(mockRepository.save(oneTestUser)).thenReturn(oneTestUser);

        assertNotNull(userService.create(dto));
    }

    @Test
    public void testingFindOne(){
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
        assertEquals(oneTestUser, userService.findOne(1L));
    }

    @Test
    public void testingGuavaPreconditions(){
        dto.setUsername(null);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> userService.create(dto));
    }

}
