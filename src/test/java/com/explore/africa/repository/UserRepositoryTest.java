package com.explore.africa.repository;

import com.explore.africa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser(){
        User user = new User("Marvin");

        User saved = userRepository.save(user);
        assertThat(saved.getId()).isNotZero();
        assertThat(saved.getName()).isEqualTo("Marvin");
    }

    @Test
    void shouldFindByName(){
        User user = new User("Catherine");
        userRepository.save(user);

        Optional<User> result = userRepository.findByName("Catherine");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Catherine");
    }

    @Test
    void shouldFailOnDuplicateName(){
        userRepository.saveAndFlush(new User("Unique"));

        assertThatThrownBy(() -> userRepository.saveAndFlush(new User("Unique")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
