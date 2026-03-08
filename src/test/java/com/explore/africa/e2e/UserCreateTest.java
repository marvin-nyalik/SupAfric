package com.explore.africa.e2e;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCreateTest {
    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc
    class UserIntegrationTest {

        @Autowired
        MockMvc mockMvc;

        @Test
        void shouldCreateUser() throws Exception {

            mockMvc.perform(post("/users/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                        {
                           "name": "Allan"
                        }
                        """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value("Allan"));
        }
    }
}
