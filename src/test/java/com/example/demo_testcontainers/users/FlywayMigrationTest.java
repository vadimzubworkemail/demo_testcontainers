//package com.example.demo_testcontainers.users;
//
//import lombok.AllArgsConstructor;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@AllArgsConstructor
//@Testcontainers
//@ActiveProfiles(value = "test-containers-flyway")
//
//public class FlywayMigrationTest {
//    @Autowired
//    UserService userService;
//
//    @Before
//    public void setUp() {
//        userService.deleteAllUsers();
//    }
//
//    @Test
//    public void shouldBeCreatedUser() {
//        final User user = new User(null, "test_login", "test_password");
//        userService.createUser(user.getLogin(), user.getEmail());
//        List<User> users = userService.loadAllUsers();
//
//    }
//}
