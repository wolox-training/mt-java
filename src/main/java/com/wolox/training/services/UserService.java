package com.wolox.training.services;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.exceptions.ObjectNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
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
     * This method returns a list of {@link User}
     *
     * @return the list of {@link User} persisted on db
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * This method returns an {@link User} with a specific Username
     *
     * @param username: user's username
     * @return an {@link User} with a specific username
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(USERS));
    }

    /**
     * This method returns an {@link User} by id
     *
     * @param id: user's id
     * @return a {@link User} by id
     */
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(USERS));
    }


    /**
     * This method creates an {@link User}
     *
     * @param userDTO: Data Transfer Object of {@link User}
     * @return the {@link User} created
     */
    @Transactional
    public User create(UserDTO userDTO) {
        User user = new User();
        adaptUserDTOToUserModel(userDTO, user);
        setEncodedPassword(user, userDTO.getPassword());
        return userRepository.save(user);
    }

    /**
     * This method deletes an {@link User}
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
     * This method updates a specific {@link User}
     *
     * @param userDto: Data Transfer Object of {@link User} with the new values
     * @param id:      user's id
     * @return the {@link User} updated
     */
    @Transactional
    public User updateUser(UserDTO userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(USERS));

        adaptUserDTOToUserModel(userDto, user);

        return userRepository.save(user);
    }

    /**
     * This method adds a {@link Book} to an {@link User}
     *
     * @param userId: user's id
     * @param bookId: id of the Book to add to an User
     * @return the {@link User} updated
     */
    @Transactional
    public User addBookToAUser(Long userId, Long bookId) {
        User user = this.findOne(userId);
        Book book = bookService.findOne(bookId);
        user.addBook(book);
        return userRepository.save(user);
    }

    /**
     * This method removes a {@link Book} from a {@link User}
     *
     * @param userId: user's id
     * @param bookId: id of the Book to remove to an User
     * @return the {@link User} updated
     */
    @Transactional
    public User removeBookToAUser(Long userId, Long bookId) {
        User user = this.findOne(userId);
        Book book = bookService.findOne(bookId);
        user.removeBook(book);
        return userRepository.save(user);
    }

    /**
     * This method sets {@link User} password
     * @param userId: user's id
     * @param password: new user's password
     * @return the {@link User} updated
     */
    @Transactional
    public User updateUserPassword(Long userId, String password){
        User user = this.findOne(userId);
        setEncodedPassword(user, password);
        return user;
    }

    private void adaptUserDTOToUserModel(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setName(userDTO.getName());
    }

    private void setEncodedPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

}
