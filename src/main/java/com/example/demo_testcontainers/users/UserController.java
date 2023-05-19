package com.example.demo_testcontainers.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> loadAllUsers() {
        return userService.loadAllUsers();
    }

    @GetMapping( path = "/{id}")
    public User loadUserById(@PathVariable UUID id) {
        return userService.loadUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody String login, @RequestBody String email) {
        return userService.createUser(login, email);
    }

    @PutMapping
    public User editUser(@RequestBody UUID id, @RequestBody String login, @RequestBody String email) {
        return userService.editUser(id, login, email);
    }

    @GetMapping
    public void deleteAllUser() {
        userService.deleteAllUsers();
    }

    @GetMapping(path = "{id}")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }
}
