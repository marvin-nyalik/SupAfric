package com.explore.africa.service;

import com.explore.africa.model.User;
import com.explore.africa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        User savedUser = new User("Marvin");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result = userService.create("Allan");

        assertThat(result.getName()).isEqualTo("Marvin");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowIfNameTooShort() {
        assertThatThrownBy(() -> userService.create("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name too short");

        verify(userRepository, never()).save(any());
    }
}