package com.wolox.training.controllers;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.models.User;
import com.wolox.training.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/loggedUser")
    @ApiOperation(value = "Logged user's username", response = String.class)
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successfully username"))
    public ResponseEntity<User> currentUserName(Principal principal) {
        User user = new User();
        user.setUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "List all users", response = User.class)
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successfully users retrieves"))
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/username")
    @ApiOperation(value = "Giving a username, return the user", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully user retrieve"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<User> findByUsername(@RequestParam(name = "username") String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an id, return the user", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully user retrieve"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        User user = userService.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Creates a user and then returns it", response = User.class)
    @ApiResponses(value = @ApiResponse(code = 201, message = "User created successfully"))
    public ResponseEntity<User> create(@RequestBody UserDTO userDto) {
        User user = userService.create(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes the user")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "User deleted successfully"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Giving an id, updates the user", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 204, message = "User updated successfully"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {
        User user = userService.updateUser(userDto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/books/add")
    @ApiOperation(value = "Giving a userId and bookId, return the user with the book added", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully user retrieve"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<User> addBook(@PathVariable(name = "id") Long userId,
            @RequestParam Long bookId) {
        User user = userService.addBookToAUser(userId, bookId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/books/remove")
    @ApiOperation(value = "Giving a userId and bookId, return the user with the book removed", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully user retrieve"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<User> removeBook(@PathVariable(name = "id") Long userId,
            @RequestParam Long bookId) {
        User user = userService.removeBookToAUser(userId, bookId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
