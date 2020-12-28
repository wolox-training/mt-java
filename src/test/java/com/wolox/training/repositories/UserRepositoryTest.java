package com.wolox.training.repositories;

import static org.junit.Assert.assertNotNull;

import com.wolox.training.models.User;
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

    private User oneTestUser;

    @BeforeEach
    public void setUp() {
        oneTestUser = new User();
        oneTestUser.setUsername("torsello");
        oneTestUser.setName("Matias Torsello");
        oneTestUser.setBirthdate(LocalDate.of(1995,10,11));
    }

    @Test
    public void whenCreateUsers_thenUsersIsPersisted(){
        User persistedUsers = userRepository.save(oneTestUser);
        assertNotNull(persistedUsers);
    }

    @Test
    public void testingGuavaPreconditions(){
       Assertions.assertThrows(NullPointerException.class, ()-> oneTestUser.setUsername(null));
    }

    @Test
    public void testingSaveObjectWithNullValues(){
        User otherUser = new User();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> userRepository.save(otherUser));
    }




}
