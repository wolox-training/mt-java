package com.wolox.training.repositories;

import static org.junit.Assert.assertNotNull;

import com.wolox.training.models.Users;
import com.wolox.training.models.Users;
import java.time.LocalDate;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users oneTestUser;

    @BeforeEach
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("torsello");
        oneTestUser.setName("Matias Torsello");
        oneTestUser.setBirthdate(LocalDate.of(1995,10,11));
    }

    @Test
    public void whenCreateUsers_thenUsersIsPersisted(){
        Users persistedUsers = userRepository.save(oneTestUser);
        assertNotNull(persistedUsers);
    }

    @Test
    public void testingGuavaPreconditions(){
       Assertions.assertThrows(NullPointerException.class, ()-> oneTestUser.setUsername(null));
    }

    @Test
    public void testingSaveObjectWithNullValues(){
        Users otherUser = new Users();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> userRepository.save(otherUser));
    }




}
