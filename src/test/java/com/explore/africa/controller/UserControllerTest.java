package com.explore.africa.controller;

import com.explore.africa.model.User;
import com.explore.africa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) //spring MVC infrastructure loaded
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc; //mock http calls

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {
        User savedUser = new User("Allan");
        ReflectionTestUtils.setField(savedUser, "id", 1L);

        when(userService.create("Allan")).thenReturn(savedUser);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Allan"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).create("Allan");
    }

    @Test
    void shouldReturnBadRequestWhenNameTooShort() throws Exception {
        User user = userService.create("Ann");
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUserById() throws Exception {
        User user = new User("Marvin");
        ReflectionTestUtils.setField(user, "id", 1L);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Marvin"));

        verify(userService).findById(1L);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        List<User> users = List.of(
                new User("Marvin"),
                new User("Allan")
        );

        ReflectionTestUtils.setField(users.get(0), "id", 1L);
        ReflectionTestUtils.setField(users.get(1), "id", 2L);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Marvin"));
    }
}
