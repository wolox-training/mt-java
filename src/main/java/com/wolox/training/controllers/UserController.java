package com.wolox.training.controllers;

import com.wolox.training.dtos.UserDTO;
import com.wolox.training.models.Users;
import com.wolox.training.services.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> findAll() {
        List<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<Users> findByUsername(@RequestParam(name = "username") String username) {
        Users user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> findOne(@PathVariable Long id) {
        Users user = userService.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody UserDTO userDto) {
        Users user = userService.create(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {
        Users user = userService.updateUser(userDto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<Users> add(@PathVariable(name = "id") Long userId, @RequestParam Long bookId) {
        Users user = userService.addBookToAUser(userId, bookId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<Users> create(@PathVariable(name = "id") Long userId, @RequestParam Long bookId) {
        Users user = userService.removeBookToAUser(userId, bookId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
