package edu.eci.dosw.tech_cup.repository;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should save user")
    void shouldSaveUser() {
        UserEntity user = new UserEntity(
                "Angela",
                "angela@mail.com",
                "123",
                true,
                LocalDateTime.now()
        );

        UserEntity saved = userRepository.save(user);

        assertNotNull(saved.getId());
    }

    @Test
    @DisplayName("should find user by email")
    void shouldFindByEmail() {
        UserEntity user = new UserEntity(
                "Angela",
                "angela@mail.com",
                "123",
                true,
                LocalDateTime.now()
        );

        userRepository.save(user);

        Optional<UserEntity> result = userRepository.findByEmail("angela@mail.com");

        assertTrue(result.isPresent());
    }

    /*
    @Test
    @DisplayName("should find users by role")
    void shouldFindByRole() {
        userRepository.save(new UserEntity("A", "a@mail.com", "123", true, LocalDateTime.now()));
        userRepository.save(new UserEntity("B", "b@mail.com", "123", true, LocalDateTime.now()));

        List<UserEntity> result = userRepository.findByRole("ADMIN");

        assertEquals(1, result.size());
    }
     */
    

    @Test
    @DisplayName("should find active users")
    void shouldFindByActive() {
        userRepository.save(new UserEntity("A", "a@mail.com", "123",  true, LocalDateTime.now()));
        userRepository.save(new UserEntity("B", "b@mail.com", "123", false, LocalDateTime.now()));

        List<UserEntity> result = userRepository.findByActive(true);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("should delete user")
    void shouldDeleteUser() {
        UserEntity user = new UserEntity(
                "Angela",
                "delete@mail.com",
                "123",
                true,
                LocalDateTime.now()
        );

        UserEntity saved = userRepository.save(user);

        userRepository.deleteById(saved.getId());

        Optional<UserEntity> result = userRepository.findById(saved.getId());

        assertFalse(result.isPresent());
    }
}