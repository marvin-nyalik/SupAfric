package com.explore.africa.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    protected User() {}

    public User(String name) {
        this.name = name;
    }
}
