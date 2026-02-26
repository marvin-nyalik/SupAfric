package com.explore.africa.service;

import com.explore.africa.exceptions.UserNotFoundException;
import com.explore.africa.model.User;
import com.explore.africa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    public User create(String name){
        return userRepository.save(new User(name));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found!")
        );
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findUser(String name, Long id){
        return userRepository.findByIdAndName(id, name).orElseThrow(
                () ->   new UserNotFoundException("User with id " + id + " and name " + name + " not found")
        );

    }
}
