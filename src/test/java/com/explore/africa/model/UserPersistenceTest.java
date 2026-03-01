package com.explore.africa.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserPersistenceTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldPersistUserAndGenerateId(){
        User user = new User("Marvin");

        User saved = entityManager.persistAndFlush(user);

        assertTrue(saved.getId() > 0);
    }
}
