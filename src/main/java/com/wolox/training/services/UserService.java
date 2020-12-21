package com.wolox.training.services;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.models.Users;
import com.wolox.training.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    public static final String USERS = "User";

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method returns a list of {@link Users}
     *
     * @return the list of {@link Users} persisted on db
     */
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    /**
     * This method returns an {@link Users} with a specific Username
     *
     * @param username: user's username
     * @return an {@link Users} with a specific username
     */
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(USERS));
    }

    /**
     * This method returns an {@link Users} by id
     *
     * @param id: user's id
     * @return a {@link Users} by id
     */
    public Users findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(USERS));
    }


    /**
     * This method creates an {@link Users}
     *
     * @param userDto: Data Transfer Object of {@link Users}
     * @return the {@link Users} created
     */
    @Transactional
    public Users create(UserDTO userDto) {
        Users user = new Users();
        adaptUsersDTOToUsersModel(userDto, user);
        return userRepository.save(user);
    }

    /**
     * This method deletes an {@link Users}
     *
     * @param id: user's id
     */
    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(USERS));
        userRepository.deleteById(id);
    }

    /**
     * This method updates a specific {@link Users}
     *
     * @param userDto: Data Transfer Object of {@link Users} with the new values
     * @param id:      user's id
     * @return the {@link Users} updated
     */
    @Transactional
    public Users updateUser(UserDTO userDto, Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(USERS));

        adaptUsersDTOToUsersModel(userDto, user);

        return userRepository.save(user);
    }

    /**
     * This method adds a {@link Book} to an {@link Users}
     *
     * @param userId: user's id
     * @param bookId: id of the Book to add to an User
     * @return the {@link Users} updated
     */
    @Transactional
    public Users addBookToAUser(Long userId, Long bookId) {
        Users user = this.findOne(userId);
        Book book = bookService.findOne(bookId);
        user.addBook(book);
        return userRepository.save(user);
    }

    /**
     * This method removes a {@link Book} from a {@link Users}
     *
     * @param userId: user's id
     * @param bookId: id of the Book to remove to an User
     * @return the {@link Users} updated
     */
    @Transactional
    public Users removeBookToAUser(Long userId, Long bookId) {
        Users user = this.findOne(userId);
        Book book = bookService.findOne(bookId);
        user.removeBook(book);
        return userRepository.save(user);
    }

    private void adaptUsersDTOToUsersModel(UserDTO userDTO, Users user) {
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }
}
