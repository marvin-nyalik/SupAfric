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
}
