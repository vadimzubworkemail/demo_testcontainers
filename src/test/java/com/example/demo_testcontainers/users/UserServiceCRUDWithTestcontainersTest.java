package com.example.demo_testcontainers.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {UserServiceCRUDWithTestcontainersTest.Initialize.class})
class UserServiceCRUDWithTestcontainersTest {
    private static final String DATABASE_NAME = "test_db";
    @Container
    public static PostgreSQLContainer<?> testDB = new PostgreSQLContainer<>("postgres:15-alpine3.18")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withUsername("postgres")
            .withPassword("postgres");
    @Autowired
    UserService userService;
    UUID id;

    @AfterEach
    public void tearDown() {
        if (id != null) {
            userService.deleteUserById(id);
        }
    }

    @Test
    public void testcontainersShouldBeRun() {
        assertThat(testDB.isRunning()).isTrue();
    }

    @Test
    public void checkIfTheListUsersIsNotNull() {
        List<User> users = userService.loadAllUsers();
        assertThat(users).isNotNull();
    }

    @Test
    public void checkUserCreation() {
        User expectedUser = userService.createUser("expected_login", "expected@email.com");
        id = expectedUser.getId();
        User actualUser = userService.loadUserById(id);
        assertThat(actualUser.getLogin()).isEqualTo(expectedUser.getLogin());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
    }

    @Test
    public void checkUserEdition() {
        User user = userService.createUser("User", "user@email.com");
        id = user.getId();
        userService.editUser(id, "edit_login", "edit@mail.net");
        User editedUser = userService.loadUserById(id);
        assertThat(editedUser.getLogin()).isEqualTo("edit_login");
        assertThat(editedUser.getEmail()).isEqualTo("edit@mail.net");
    }

    @Test
    public void checkingUserDeletionById() {
        User currentUser = userService.createUser("current_login", "current@mail.ru");
        UUID currentId = currentUser.getId();
        userService.deleteUserById(currentId);
        List<User> users = userService.loadAllUsers();
        for (User user : users) {
            assertThat(user.getId()).isNotEqualTo(currentId);
        }
    }

    @Test
    public void CheckingForAnExceptionIsThrownForAnInvalidUser() {
        UUID invalidId = randomUUID();
        Throwable throwable = catchThrowable(() -> userService.loadUserById(invalidId));

        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }

    static class Initialize implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.username=" + testDB.getUsername(),
                    "spring.datasource.password=" + testDB.getPassword(),
                    "spring.datasource.url=" + testDB.getJdbcUrl()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}