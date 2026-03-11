package com.explore.africa.e2e;

import com.explore.africa.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void shouldCreateUserAndRetrieveIt() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                        """
                            {
                               "name": "Allan"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Allan"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserResponse createdUser =
                objectMapper.readValue(json, UserResponse.class);

        Long userId = createdUser.id();
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Allan"));
    }
}