package com.example.demo_testcontainers.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    UUID uuid;

    @AfterEach
    public void tearDown() {
        if (uuid != null) {
            userService.deleteUserById(uuid);
        }
    }

    @Test
    public void getUserList() {
        List<User> users = userService.loadAllUsers();
        assertThat(users).isNotNull();
        assertThat(users.size()).isNotNull();
    }

    @Test
    public void checkThatUserWasCreated() {
        User user = userService.createUser("Vasya Pupkin", "pupkin@email.com");
        uuid = user.getId();
        assertThat(user.getLogin()).isEqualTo("Vasya Pupkin");
        assertThat(user.getEmail()).isEqualTo("pupkin@email.com");
    }

    @Test
    public void checkThatTheUserWasEdited() {
        User user = userService.createUser("Dodik", "dodik@email.com");
        uuid = user.getId();
        User editedUser = userService.editUser(uuid, "Borya", "borya@mail.net");
        assertThat(editedUser.getEmail()).isEqualTo("borya@mail.net");
        assertThat(editedUser.getLogin()).isEqualTo("Borya");
    }
}