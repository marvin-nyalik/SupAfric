package com.explore.africa.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserValidationTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldFailIfNameTooShort(){
        User user = new User("John");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        assertThat(violations).hasSize(1);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("Username must be at least 5 characters long");

        assertThat(violations)
                .extracting(v -> v.getPropertyPath() + " : " + v.getMessage())
                .containsExactlyInAnyOrder(
                        "name : Username must be at least 5 characters long"
                );
    }

    @Test
    void shouldPassWhenNameIsValid(){
        User user = new User("Marvin");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
}
